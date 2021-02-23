package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.model.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class OffsetCellDefinitionWrapper<T> implements CellDefinition<T> {
    private final CellDefinition<T> wrapped;
    private final Point offset;

    @Override
    public CellDefinition<?> getDefinitionForField(String name) {
        return wrapped.getDefinitionForField(name);
    }

    @Override
    public Set<String> getFields() {
        return wrapped.getFields();
    }

    @Override
    public List<String> resolveCell(Point origin) {
        return wrapped.resolveCell(origin.add(offset));
    }

    @Override
    public T convertValue(Iterator<ValueRange> values) {
        return wrapped.convertValue(values);
    }
}
