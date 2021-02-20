package de.fallapalooza.streamapi.annotation.processor;

/**
 * A Cell Definition, which describes both what cells to get, and how to turn them into an object
 * @param <T> The type of object
 */
public interface CellDefinition<T> extends CellResolver, ObjectResolver<T> {
}
