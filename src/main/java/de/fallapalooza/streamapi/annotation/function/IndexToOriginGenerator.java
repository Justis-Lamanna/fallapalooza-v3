package de.fallapalooza.streamapi.annotation.function;

import de.fallapalooza.streamapi.annotation.model.Point;

/**
 * A generator which takes the index of an sub-object, and calculates the origin of the sub-object
 */
@FunctionalInterface
public interface IndexToOriginGenerator {
    /**
     * Calculate the origin point
     * @param index The index of the sub-object
     * @return The origin point for that sub-object
     */
    Point getOrigin(int index);
}
