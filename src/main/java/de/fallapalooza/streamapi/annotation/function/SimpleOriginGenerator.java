package de.fallapalooza.streamapi.annotation.function;

import de.fallapalooza.streamapi.annotation.model.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A simple IndexToOriginGenerator which performs general offset logic
 * The origin is calculated by:
 * calcRow = row + (rowOffset * index)
 * calcCol = col + (colOffset * index)
 * As such, setting rowOffset or colOffset to 0 indicates a constant, and nonzero
 * results in a scrolling action (either horizontal, vertical, or diagonal) as index increases
 */
@RequiredArgsConstructor
@Getter
public class SimpleOriginGenerator implements IndexToOriginGenerator {
    private final int row;
    private final int rowOffset;
    private final int col;
    private final int colOffset;

    @Override
    public Point getOrigin(int index) {
        int calcRow = row + (rowOffset * index);
        int calcCol = col + (colOffset * index);
        return new Point(calcRow, calcCol);
    }
}
