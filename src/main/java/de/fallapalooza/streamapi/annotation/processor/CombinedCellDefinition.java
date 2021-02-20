package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.model.Point;
import lombok.Data;

import java.util.Iterator;
import java.util.List;

/**
 * A basic CellDefinition which delegates to each of its parts
 * @param <T> The type created by this definition
 */
@Data
public class CombinedCellDefinition<T> implements CellDefinition<T> {
    private final CellResolver resolver;
    private final ObjectResolver<T> objectResolver;

    @Override
    public List<String> resolveCell(Point origin) {
        return resolver.resolveCell(origin);
    }

    @Override
    public T convertValue(Iterator<ValueRange> values) {
        return objectResolver.convertValue(values);
    }
}
