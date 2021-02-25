package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.Cell;
import de.fallapalooza.streamapi.annotation.Generator;
import de.fallapalooza.streamapi.annotation.Nested;
import de.fallapalooza.streamapi.annotation.Sheet;
import de.fallapalooza.streamapi.annotation.function.IndexToOriginGenerator;
import de.fallapalooza.streamapi.annotation.function.SimpleOriginGenerator;
import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.util.ValueRangeUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Uses annotations to compile a CellDefinition from a class file.
 */
@Component
public class AnnotationBasedCellDefinitionCompiler implements CellDefinitionCompiler {
    @Autowired
    private ConversionService conversionService;

    private final Map<Class<?>, CellDefinition<?>> cache = new HashMap<>();

    @Override
    public <T> CellDefinition<T> compile(Class<T> clazz) {
        if(!cache.containsKey(clazz)) {
            FieldBasedCallback callback = new FieldBasedCallback();
            ReflectionUtils.doWithFields(clazz, callback);
            CellDefinition<T> constructedResolver = new NestedCellDefinition<T>(clazz, callback.definitions);
            cache.put(clazz, constructedResolver);
        }
        return (CellDefinition<T>) cache.get(clazz);
    }

    @Getter
    private class FieldBasedCallback implements ReflectionUtils.FieldCallback {
        private final Map<String, CellDefinition<?>> definitions = new LinkedHashMap<>();

        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            if(field.isAnnotationPresent(Cell.class)) {
                Cell annotation = field.getAnnotation(Cell.class);
                if(isPrimitive(field.getType())) {
                    // Single primitive object
                    Point origin = new Point(annotation.row(), annotation.col());
                    CellResolver cellAnnotationCellResolver = new SingleCellResolver(origin, getSheetName(field.getDeclaringClass(), annotation.sheet()));
                    ObjectResolver<?> cellAnnotationObjectResolver = getSingleCellResolverForType(field.getType());

                    CellDefinition<?> cellDefinition = new SingleCellDefinition<>(cellAnnotationCellResolver, cellAnnotationObjectResolver);
                    definitions.put(field.getName(), cellDefinition);
                } else {
                    // One single complex object, which needs further parsing
                    CellDefinition<?> subDefinition = compile(field.getType());
                    definitions.put(field.getName(), subDefinition);
                }
            } else if(field.isAnnotationPresent(Nested.class)) {
                Nested annotation = field.getAnnotation(Nested.class);
                IndexToOriginGenerator generator = getGeneratorForAnnotation(annotation);
                if(isPrimitive(annotation.type())) {
                    CellDefinition<?> definition = new ArrayCellDefinition<>(
                            new SingleCellDefinition<>(
                                    CellResolver.identity(getSheetName(field.getDeclaringClass(), annotation.sheet())),
                                    getSingleCellResolverForType(field.getType())),
                            annotation.length(), generator, getCollectorForOutput(field.getType()));
                    definitions.put(field.getName(), definition);
                } else {
                    // Array of complex objects, which need further parsing.
                    CellDefinition<?> subDefinition = compile(annotation.type());
                    CellDefinition<?> definition = new ArrayCellDefinition<>(subDefinition, annotation.length(), generator, getCollectorForOutput(field.getType()));
                    definitions.put(field.getName(), definition);
                }
            }
        }

        private <T, C> Collector<T, ?, C> getCollectorForOutput(Class<C> type) {
            if(List.class.equals(type)) {
                return (Collector<T, ?, C>) Collectors.toList();
            }
            throw new IllegalArgumentException("Cannot collect to list of type " + type.getCanonicalName());
        }

        private String getSheetName(Class<?> clazz, String sheet) {
            if(sheet.isEmpty()) {
                if(clazz.isAnnotationPresent(Sheet.class)) {
                    String classLevelSheet = clazz.getAnnotation(Sheet.class).value();
                    if(!classLevelSheet.isEmpty()) {
                        return classLevelSheet;
                    }
                }
                return null;
            } else {
                return sheet;
            }
        }

        private boolean isPrimitive(Class<?> type) {
            return String.class.equals(type) || ClassUtils.isPrimitiveOrWrapper(type);
        }

        private <T> ObjectResolver<T> getSingleCellResolverForType(Class<T> type) {
            return iterable -> {
                List<Object> flattenedValues = ValueRangeUtils.flatten(iterable.next());
                if(flattenedValues.isEmpty()) {
                    return null;
                } else if(conversionService.canConvert(flattenedValues.get(0).getClass(), type)){
                    return conversionService.convert(flattenedValues.get(0), type);
                } else {
                    throw new IllegalArgumentException("Can't convert cell of type " +
                            flattenedValues.get(0).getClass().getCanonicalName() + " to " + type.getCanonicalName());
                }
            };
        }

        private IndexToOriginGenerator getGeneratorForAnnotation(Nested annotation) {
            if(Nested.EmptyGenerator.class.equals(annotation.generatorClass())) {
                Generator generator = annotation.generator();
                return new SimpleOriginGenerator(generator.row(), generator.rowOffset(), generator.col(), generator.colOffset());
            } else {
                try {
                    return annotation.generatorClass().newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new IllegalArgumentException("Cannot instantiate generator class " + annotation.generatorClass().getCanonicalName());
                }
            }
        }
    }
}
