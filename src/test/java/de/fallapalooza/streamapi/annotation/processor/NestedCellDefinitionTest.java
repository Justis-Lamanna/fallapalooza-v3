package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.util.ValueRangeUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NestedCellDefinitionTest {
    private static final Map<String, CellDefinition<?>> SUBDEFS = new LinkedHashMap<>();

    static {
        SUBDEFS.put("one", new ConstantCellDefinition<>(new Point(1, 1), 1));
        SUBDEFS.put("two", new ConstantCellDefinition<>(new Point(1, 2), 2));
        SUBDEFS.put("three", new ConstantCellDefinition<>(new Point(1, 3), 3));
    }

    @Test
    public void resolveCellWorksCorrectly() {
        NestedCellDefinition<Bed> definition = new NestedCellDefinition<>(Bed.class, SUBDEFS);
        List<String> cells = definition.resolveCell(Point.ZERO);

        assertTrue(cells.contains("A1"));
        assertTrue(cells.contains("B1"));
        assertTrue(cells.contains("C1"));
    }

    @Test
    public void convertValueWorksCorrectly() {
        List<ValueRange> values = Arrays.asList(
                ValueRangeUtils.fromConstant(1),
                ValueRangeUtils.fromConstant(2),
                ValueRangeUtils.fromConstant(3)
        );
        NestedCellDefinition<Bed> definition = new NestedCellDefinition<>(Bed.class, SUBDEFS);
        Bed bed = definition.convertValue(values.iterator());

        assertEquals(1, bed.one);
        assertEquals(2, bed.two);
        assertEquals(3, bed.three);
    }

    @Test
    public void getFieldsWorksCorrectly() {
        NestedCellDefinition<Bed> definition = new NestedCellDefinition<>(Bed.class, SUBDEFS);
        Set<String> fields = definition.getFields();
        assertEquals(3, fields.size());
        assertTrue(fields.contains("one"));
        assertTrue(fields.contains("two"));
        assertTrue(fields.contains("three"));
    }

    @Test
    public void getDefinitionFieldForValidValue() {
        NestedCellDefinition<Bed> definition = new NestedCellDefinition<>(Bed.class, SUBDEFS);
        assertNotNull(definition.getDefinitionForField("one"));
    }

    @Test
    public void getDefinitionFieldForInvalidValue() {
        NestedCellDefinition<Bed> definition = new NestedCellDefinition<>(Bed.class, SUBDEFS);
        assertNull(definition.getDefinitionForField("asdf"));
    }

    public static class Bed {
        int one;
        int two;
        int three;
    }
}