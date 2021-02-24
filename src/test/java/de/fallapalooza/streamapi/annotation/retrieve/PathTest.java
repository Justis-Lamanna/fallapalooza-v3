package de.fallapalooza.streamapi.annotation.retrieve;

import de.fallapalooza.streamapi.annotation.Bed;
import de.fallapalooza.streamapi.annotation.exception.IllegalPathException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {
    @Test
    public void returnsPathsByDotExpression() {
        Path<Bed, Integer> path = Path.fromExpression("mattress.one");

        assertNotNull(path);
        assertNotNull(path.resolve(Bed.DEFINITION));
    }

    @Test
    public void returnsPathByFieldList() {
        Path<Bed, Integer> path = Path.fromFields("mattress", "one");

        assertNotNull(path);
        assertNotNull(path.resolve(Bed.DEFINITION));
    }

    @Test
    public void invalidPathThrowsException() {
        Path<Bed, Integer> path = Path.fromExpression("bad");

        assertThrows(IllegalPathException.class, () -> {
            path.resolve(Bed.DEFINITION);
        });
    }
}