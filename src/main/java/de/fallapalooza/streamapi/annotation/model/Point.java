package de.fallapalooza.streamapi.annotation.model;

import lombok.Data;

/**
 * Represents a point in a spreadsheet
 */
@Data
public class Point {
    public static final Point ZERO = new Point(0, 0);
    public static final Point ONE = new Point(1, 1);

    private final int row;
    private final int col;

    /**
     * Add a point to this point
     * @param other The point to add
     * @return A new point, the sum total of this row/col and the supplied row/col
     */
    public Point add(Point other) {
        return new Point(this.row + other.row, this.col + other.col);
    }

    /**
     * Add a number of rows to this point
     * @param row The number of rows to add
     * @return A new point, at the same column as this point, but at a row at the total of this row + the supplied value
     */
    public Point addRow(int row) {
        return new Point(this.row + row, this.col);
    }

    /**
     * Add a number of columns to this point
     * @param col The number of rows to columns
     * @return A new point, at the same row as this point, but at a column at the total of this column + the supplied value
     */
    public Point addCol(int col) {
        return new Point(this.row, this.col + col);
    }

    /**
     * Convert this point to an A1 notation
     * @return This point, in A1 notation
     */
    public String toExcel() {
        return getExcelColumnFromNumber(col) + row;
    }

    /**
     * Convert this point to an A1 notation, optionally specifying a sheet name for multi-sheet applications
     * @return This point, in A1 notation, with sheet name
     */
    public String toExcelWithSheet(String sheet) {
        return sheet + "!" + toExcel();
    }

    private String getExcelColumnFromNumber(int number) {
        StringBuilder s = new StringBuilder();
        while (number > 0) {
            int remainder = (number - 1) % 26;
            number = (number - 1) / 26;
            s.insert(0, (char)(65 + remainder));
        }
        return s.toString();
    }
}
