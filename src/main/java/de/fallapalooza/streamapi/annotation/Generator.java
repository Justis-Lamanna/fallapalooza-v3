package de.fallapalooza.streamapi.annotation;

/**
 * A basic Origin Generator, using the common logic of value = constant + (multiplier * index)
 */
public @interface Generator {
    /**
     * The row to start counting from
     * @return The row to start from
     */
    int row();

    /**
     * The number of rows to skip between elements. If unspecified, none are skipped
     * @return The offset of the row.
     */
    int rowOffset() default 0;

    /**
     * The column to start counting from
     * @return The column to start from
     */
    int col();

    /**
     * The number of columns to skip between elements. If unspecified, none are skipped
     * @return The offset of the row.
     */
    int colOffset() default 0;
}
