package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.model.Point;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ConstantCellDefinition<T> implements CellDefinition<T> {
    private final Point cell;
    private final T constant;

    public ConstantCellDefinition(Point cell, T constant) {
        this.cell = cell;
        this.constant = constant;
    }

    public ConstantCellDefinition(T constant) {
        cell = Point.ZERO;
        this.constant = constant;
    }

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
        return Collections.singletonList(cell.add(origin).toExcel());
    }

    @Override
    public T convertValue(Iterator<ValueRange> values) {
        //Advance forward, despite the fact that we are completely ignoring this field.
        values.next();
        return constant;
    }
}
