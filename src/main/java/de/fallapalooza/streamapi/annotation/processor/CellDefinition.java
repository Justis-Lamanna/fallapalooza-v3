package de.fallapalooza.streamapi.annotation.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.convert.TypeDescriptor;

import java.util.Set;

/**
 * A Cell Definition, which describes both what cells to get, and how to turn them into an object
 * @param <T> The type of object
 */
public interface CellDefinition<T> extends CellResolver, ObjectResolver<T> {
    /**
     * Get the CellDefinition for a specific field
     * @param name The field name
     * @return The CellDefinition for that field
     */
    CellDefinition<?> getDefinitionForField(String name);

    /**
     * Get the CellDefinition for a specific field
     * @param name The field name
     * @param clazz The expected class
     * @return The CellDefinition for that field
     */
    default <U> CellDefinition<U> getDefinitionForField(String name, Class<U> clazz) {
        return (CellDefinition<U>) getDefinitionForField(name);
    }

    /**
     * Get the CellDefinition for a specific field
     * @param name The field name
     * @param ref The expected class
     * @return The CellDefinition for that field
     */
    default <U> CellDefinition<U> getDefinitionForField(String name, TypeReference<U> ref) {
        return (CellDefinition<U>) getDefinitionForField(name);
    }

    /**
     * Get all the fields in this definition
     * @return The set of field names
     */
    Set<String> getFields();
}
