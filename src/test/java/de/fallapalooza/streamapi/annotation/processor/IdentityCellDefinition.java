package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.util.ValueRangeUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class IdentityCellDefinition<T> implements CellDefinition<T> {
    @Override
    public CellDefinition<?> getDefinitionForField(String name) {
        return null;
    }

    @Override
    public Set<String> getFields() {
        return Collections.emptySet();
    }

    @Override
    public List<String> resolveCell(Point origin) {
        return Collections.singletonList(origin.toExcel());
    }

    @Override
    public T convertValue(Iterator<ValueRange> values) {
        return (T) ValueRangeUtils.flatten(values.next()).get(0);
    }
}
