package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.Iterator;

/**
 * Used in tandem with a CellResolver, to convert the retrieved values into the final object
 * @param <T> The type converted to
 */
public interface ObjectResolver<T> {
    /**
     * Convert the requested cells into a value
     * @param values The cell values requested
     * @return The constructed object
     */
    T convertValue(Iterator<ValueRange> values);
}
