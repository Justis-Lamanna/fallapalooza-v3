package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SingleCellResolverTest {
    @Test
    public void resolveShouldCalculateCellNoSheet() {
        SingleCellResolver resolver = new SingleCellResolver(new Point(1, 1));
        List<String> cells = resolver.resolveCell(Point.ZERO);

        assertEquals(1, cells.size());
        assertEquals("A1", cells.get(0));
    }

    @Test
    public void resolveShouldCalculateCell() {
        SingleCellResolver resolver = new SingleCellResolver(new Point(1, 1), "test");
        List<String> cells = resolver.resolveCell(Point.ZERO);

        assertEquals(1, cells.size());
        assertEquals("test!A1", cells.get(0));
    }

    @Test
    public void resolveShouldCalculateCellNonZeroOrigin() {
        SingleCellResolver resolver = new SingleCellResolver(new Point(1, 1));
        List<String> cells = resolver.resolveCell(new Point(1, 1));

        assertEquals(1, cells.size());
        assertEquals("B2", cells.get(0));
    }
}