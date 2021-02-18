package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.model.Point;

import java.util.List;

/**
 * A formula which calculates the actual cell values, given some origin.
 */
public interface CellResolver {
    /**
     * Calculate one or more cells that correspond to the constructed object
     * @param origin The origin to calculate relative to
     * @return A list of cells corresponding to this constructed object
     */
    List<String> resolveCell(Point origin);
}
