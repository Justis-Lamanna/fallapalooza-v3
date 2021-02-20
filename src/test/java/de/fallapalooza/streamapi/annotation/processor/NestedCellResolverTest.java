package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.function.SimpleOriginGenerator;
import de.fallapalooza.streamapi.annotation.model.Point;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NestedCellResolverTest {
    @Test
    void resolvesToCorrectCellList() {
        NestedCellResolver resolver = new NestedCellResolver(new SingleCellResolver(Point.ZERO), 5, new SimpleOriginGenerator(0, 1, 0, 0));
        List<String> a1s = resolver.resolveCell(new Point(1, 1));

        assertIterableEquals(Arrays.asList("A1", "A2", "A3", "A4", "A5"), a1s);
    }
}