package de.fallapalooza.streamapi.annotation.retrieve;

import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service which retrieves an object based on its definition
 */
public interface RetrieveService {
    /**
     * Retrieve an object, depending on a definition and origin
     * @param definition The definition of the object to get
     * @param origin The origin point to use
     * @param <T> The type returned
     * @return The retrieved object
     */
    <T> T retrieve(CellDefinition<T> definition, Point origin);

    /**
     * Retrieve multiple objects, depending on multiple definitions
     * @param definitions The definitions to get
     * @param <T> The type returned
     * @return The retrieved objects
     */
    <T> List<T> bulkRetrieve(List<CellDefinition<T>> definitions);

    /**
     * Return multiple objects, with a common root and multiple paths
     * @param root The root to retrieve
     * @param paths The paths to retrieve
     * @param <T> The root type
     * @param <OUT> The output type
     * @return The retrieved path resolutions
     */
    default <T, OUT> List<OUT> bulkRetrieve(CellDefinition<T> root, List<Path<T, OUT>> paths) {
        List<CellDefinition<OUT>> newDefinitions = paths.stream()
                .map(path -> path.resolve(root))
                .collect(Collectors.toList());
        return bulkRetrieve(newDefinitions);
    }

    /**
     * Retrieve an object, depending on a definition
     * By default, the origin is 0,0
     * @param definition The definition of the object to get
     * @param <T> The type returned
     * @return The retrieved object
     */
    default <T> T retrieve(CellDefinition<T> definition) {
        return retrieve(definition, Point.ZERO);
    }

    /**
     * Retrieve a partial object, by a definition and path
     * @param definition The starting definition
     * @param path The path to take for the partial object
     * @param <START> The type of object the starting definition returns
     * @param <END> The type returned after traversing the path
     * @return The retrieved partial object
     */
    default <START, END> END retrieve(CellDefinition<START> definition, Path<START, END> path) {
        CellDefinition<END> resolved = path.resolve(definition);
        return retrieve(resolved);
    }

    /**
     * Retrieve a partial object, by a definition, origin and path
     * @param definition The starting definition
     * @param path The path to take for the partial object
     * @param origin The origin point to use
     * @param <START> The type of object the starting definition returns
     * @param <END> The type returned after traversing the path
     * @return The retrieved partial object
     */
    default <START, END> END retrieve(CellDefinition<START> definition, Path<START, END> path, Point origin) {
        CellDefinition<END> resolved = path.resolve(definition);
        return retrieve(resolved, origin);
    }
}
