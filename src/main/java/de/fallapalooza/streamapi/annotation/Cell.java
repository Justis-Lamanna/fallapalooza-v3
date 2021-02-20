package de.fallapalooza.streamapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a static Cell, relative to the object's origin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {
    /**
     * The row of the cell, relative to the object's origin
     * @return The row of the cell
     */
    int row();

    /**
     * The column of the cell, relative to the object's origin
     * @return The column of the cell
     */
    int col();

    /**
     * The name of the sheet this cell is on
     * @return The name of the sheet
     */
    String sheet() default "";
}
