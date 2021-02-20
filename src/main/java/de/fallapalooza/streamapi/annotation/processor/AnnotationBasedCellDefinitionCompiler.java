package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.Cell;
import de.fallapalooza.streamapi.annotation.Generator;
import de.fallapalooza.streamapi.annotation.Nested;
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
import java.util.List;
import java.util.Map;

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
            CellDefinition<T> constructedResolver = new NestedObjectResolver<T>(clazz, callback.definitions);
            cache.put(clazz, constructedResolver);
        }
        return (CellDefinition<T>) cache.get(clazz);
    }

    @Getter
    private class FieldBasedCallback implements ReflectionUtils.FieldCallback {
        private final Map<String, CellDefinition<?>> definitions = new HashMap<>();

        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            if(field.isAnnotationPresent(Cell.class)) {
                Cell annotation = field.getAnnotation(Cell.class);
                if(isPrimitive(field.getType())) {
                    // Single primitive object
                    Point origin = new Point(annotation.row(), annotation.col());
                    CellResolver cellAnnotationCellResolver = new SingleCellResolver(origin, getSheetName(annotation.sheet()));
                    ObjectResolver<?> cellAnnotationObjectResolver = getSingleCellResolverForType(field.getType());

                    CellDefinition<?> cellDefinition = new CombinedCellDefinition<>(cellAnnotationCellResolver, cellAnnotationObjectResolver);
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
                    // Straight-up array of normal objects.
                    CellResolver nestedAnnotationCellResolver =
                            new NestedCellResolver(new SingleCellResolver(Point.ZERO, getSheetName(annotation.sheet())), annotation.length(), generator);
                    ObjectResolver<?> cellAnnotationObjectResolver = getSingleCellResolverForType(field.getType());

                    definitions.put(field.getName(), new CombinedCellDefinition<>(nestedAnnotationCellResolver, cellAnnotationObjectResolver));
                } else {
                    // Array of complex objects, which need further parsing.
                    CellDefinition<?> subDefinition = compile(annotation.type());
                    CellResolver nestedAnnotationCellResolver =
                            new NestedCellResolver(subDefinition, annotation.length(), generator);
                    definitions.put(field.getName(), new CombinedCellDefinition<>(nestedAnnotationCellResolver, subDefinition));
                }
            }
        }

        private String getSheetName(String sheet) {
            return sheet.isEmpty() ? null : sheet;
        }

        private boolean isPrimitive(Class<?> type) {
            return String.class.equals(type) || ClassUtils.isPrimitiveOrWrapper(type);
        }

        private <T> ObjectResolver<T> getSingleCellResolverForType(Class<T> type) {
            if(!conversionService.canConvert(String.class, type)) {
                throw new IllegalArgumentException("Cannot convert between String.class and field type " + type.getCanonicalName());
            }
            return iterable -> {
                List<String> flattenedValues = ValueRangeUtils.flatten(iterable.next());
                if(flattenedValues.isEmpty()) {
                    return null;
                } else {
                    return conversionService.convert(flattenedValues.get(0), type);
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
