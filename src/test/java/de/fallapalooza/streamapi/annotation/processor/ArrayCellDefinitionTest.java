package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.function.SimpleOriginGenerator;
import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.util.ValueRangeUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ArrayCellDefinitionTest {
    private static final SimpleOriginGenerator SCROLL_ONE_ROW = new SimpleOriginGenerator(0, 1, 0, 0);

    private static final CellDefinition<Integer> SUB_DEFINITION = new IdentityCellDefinition<>();
    private static final ArrayCellDefinition<Integer, List<Integer>> DEFINITION =
            new ArrayCellDefinition<>(SUB_DEFINITION, 5, SCROLL_ONE_ROW, Collectors.toList());

    @Test
    public void resolveCellWorksCorrectly() {
        List<String> cells = DEFINITION.resolveCell(Point.ONE);

        assertIterableEquals(Arrays.asList("A1", "A2", "A3", "A4", "A5"), cells);
    }

    @Test
    public void convertValueWorksCorrectly() {
        List<ValueRange> values = Arrays.asList(
                ValueRangeUtils.fromConstant(1),
                ValueRangeUtils.fromConstant(2),
                ValueRangeUtils.fromConstant(3),
                ValueRangeUtils.fromConstant(4),
                ValueRangeUtils.fromConstant(5)
                );

        List<Integer> parsed = DEFINITION.convertValue(values.iterator());

        assertIterableEquals(Arrays.asList(1, 2, 3, 4, 5), parsed);
    }

    @Test
    public void getFieldsWorksCorrectly() {
        Set<String> fields = DEFINITION.getFields();
        assertEquals(5, fields.size());
        assertTrue(fields.contains("0"));
        assertTrue(fields.contains("1"));
        assertTrue(fields.contains("2"));
        assertTrue(fields.contains("3"));
        assertTrue(fields.contains("4"));
    }

    @Test
    public void getDefinitionForFieldWorksForGoodIndex() {
        assertNotNull(DEFINITION.getDefinitionForField("0"));
    }

    @Test
    public void getDefinitionForFieldForNonNumber() {
        assertNull(DEFINITION.getDefinitionForField("asdf"));
    }

    @Test
    public void getDefinitionForFieldForNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            DEFINITION.getDefinitionForField("-1");
        });
    }
}