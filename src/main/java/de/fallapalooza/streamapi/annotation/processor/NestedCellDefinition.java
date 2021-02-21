package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.model.Point;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A CellDefinition which constructs a complex object via additional cell definitions
 * @param <T> The type of class
 */
@Getter
public class NestedCellDefinition<T> implements CellDefinition<T> {
    private final Class<T> clazz;
    private final Map<String, CellDefinition<?>> definitions;

    /**
     * Create this resolver
     * @param clazz The class to construct
     * @param definitions The sub-definitions, keyed by field name
     */
    public NestedCellDefinition(Class<T> clazz, Map<String, CellDefinition<?>> definitions) {
        this.clazz = clazz;
        this.definitions = new TreeMap<>(definitions);
    }

    @Override
    public List<String> resolveCell(Point origin) {
        return definitions.values().stream()
                .map(resolver -> resolver.resolveCell(origin))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public T convertValue(Iterator<ValueRange> values) {
        T obj;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Resolver could not instantiate object of type " + clazz.getCanonicalName(), e);
        }

        for(String fieldName : definitions.keySet()) {
            Field field;
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("Unable to retrieve field " + fieldName, e);
            }
            CellDefinition<?> subDefinition = definitions.get(fieldName);
            Object createdObj = subDefinition.convertValue(values);
            try {
                field.set(obj, createdObj);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Unable to set field " + fieldName, e);
            }
        }

        return obj;
    }

    @Override
    public CellDefinition<?> getDefinitionForField(String name) {
        return definitions.get(name);
    }

    @Override
    public Set<String> getFields() {
        return definitions.keySet();
    }
}
