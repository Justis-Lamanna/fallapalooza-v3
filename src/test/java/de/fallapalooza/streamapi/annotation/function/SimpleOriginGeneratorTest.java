package de.fallapalooza.streamapi.annotation.function;

import de.fallapalooza.streamapi.annotation.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleOriginGeneratorTest {
    @Test
    public void testGetOriginRowIncrement() {
        SimpleOriginGenerator generator = new SimpleOriginGenerator(0, 1, 0, 0);
        Point first = generator.getOrigin(0);
        Point second = generator.getOrigin(1);
        Point third = generator.getOrigin(2);

        assertEquals(0, first.getRow());
        assertEquals(1, second.getRow());
        assertEquals(2, third.getRow());
    }

    @Test
    public void testGetOriginColumnIncrement() {
        SimpleOriginGenerator generator = new SimpleOriginGenerator(0, 0, 0, 1);
        Point first = generator.getOrigin(0);
        Point second = generator.getOrigin(1);
        Point third = generator.getOrigin(2);

        assertEquals(0, first.getCol());
        assertEquals(1, second.getCol());
        assertEquals(2, third.getCol());
    }
}