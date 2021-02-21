package de.fallapalooza.streamapi.annotation.processor;

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
     * Get all the fields in this definition
     * @return The set of field names
     */
    Set<String> getFields();
}
