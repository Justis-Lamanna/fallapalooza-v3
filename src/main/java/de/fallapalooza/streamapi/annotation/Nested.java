package de.fallapalooza.streamapi.annotation;

import de.fallapalooza.streamapi.annotation.function.IndexToOriginGenerator;
import de.fallapalooza.streamapi.annotation.model.Point;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a finite collection of sub-objects
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Nested {
    /**
     * The sub-object's type
     * For List<T>, this is T
     * @return The sub-object's type
     */
    Class<?> type();

    /**
     * The number of elements in this list
     * @return The number of elements
     */
    int length();

    /**
     * A specific sheet the cell(s) are on
     * @return The sheets the cells are on
     */
    String sheet() default "";

    /**
     * A complex function which creates origin points based on position in the list
     * @return A generator function.
     */
    Class<? extends IndexToOriginGenerator> generatorClass() default EmptyGenerator.class;

    /**
     * A basic generator which creates origin points based on list position
     * @return basic Generator credentials
     */
    Generator generator() default @Generator(row = 0, col = 0);

    /**
     * An empty generator which simply marks that no custom one was specified.
     */
    class EmptyGenerator implements IndexToOriginGenerator {
        @Override
        public Point getOrigin(int index) {
            return new Point(0, 0);
        }
    }
}
