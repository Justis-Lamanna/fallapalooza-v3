package de.fallapalooza.streamapi.annotation.retrieve;

import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
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

    default <T, A, B> Tuple2<A, B> bulkRetrieve(CellDefinition<T> root, Path<T, A> one, Path<T, B> two) {
        List<CellDefinition<?>> definitions = Arrays.asList(
                one.resolve(root),
                two.resolve(root)
        );
        List<?> objs = bulkRetrieveGeneric(definitions);
        return Tuples.of((A)objs.get(0), (B)objs.get(1));
    }

    /**
     * Return multiple objects, with a common root and multiple paths
     * @param definitions The definitions to retrieve
     * @param <T> The root type
     * @return The retrieved path resolutions
     */
    <T> List<Object> bulkRetrieveGeneric(List<CellDefinition<?>> definitions);

    /**
     * Return multiple objects, with a common root and multiple paths
     * @param root The root to retrieve
     * @param paths The paths to retrieve
     * @param <T> The root type
     * @return The retrieved path resolutions
     */
    default <T> List<Object> bulkRetrieveGeneric(CellDefinition<T> root, List<Path<T, ?>> paths) {
        List<CellDefinition<?>> newDefinitions = paths.stream()
                .map(path -> path.resolve(root))
                .collect(Collectors.toList());
        return bulkRetrieveGeneric(newDefinitions);
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

    /**
     * Perform a query: if a field matches a predicate, return another field
     * @param definition The Cell Definition
     * @param fieldToQuery A path to the field to query
     * @param predicate A predicate to test the value of the fieldToQuery
     * @param fieldToReturn A path to the field to return
     * @param <T> The root type
     * @param <F1> The type of the queried field
     * @param <F2> The type of the returned field
     * @return The returned field, or null
     */
    default <T, F1, F2> F2 query(CellDefinition<T> definition, Path<T, List<F1>> fieldToQuery, Predicate<F1> predicate, Path<T, List<F2>> fieldToReturn) {
        Tuple2<List<F1>, List<F2>> retrieved = bulkRetrieve(definition, fieldToQuery, fieldToReturn);
        for(int idx = 0; idx < retrieved.getT1().size(); idx++) {
            if(predicate.test(retrieved.getT1().get(idx))) {
                return retrieved.getT2().get(idx);
            }
        }
        return null;
    }
}
