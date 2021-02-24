package de.fallapalooza.streamapi.annotation.retrieve;

import de.fallapalooza.streamapi.annotation.exception.IllegalPathException;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Encapsulates a path to a partial object
 * @param <TO> The starting definition
 * @param <FROM> The ending definition
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Path<TO, FROM> {
    private static final Path<Object, Object> ROOT = new Path<>(Collections.emptyList());

    private final List<String> paths;

    /**
     * Get a modified CellDefinition that gets the partial object from a start
     * @param start The starting CellDefinition
     * @return The modified CellDefinition pointing to the requested object
     */
    public CellDefinition<FROM> resolve(CellDefinition<TO> start) {
        CellDefinition<?> current = start;
        for(String path : paths) {
            current = current.getDefinitionForField(path);
            if(current == null) {
                throw new IllegalPathException(this);
            }
        }
        return (CellDefinition<FROM>) current;
    }

    /**
     * Relative to this path, retrieve the next field down
     * @param next The next field
     * @param <NEWFROM> The type of the next field
     * @return A new path, composed of this path and the next string
     */
    public <NEWFROM> Path<TO, NEWFROM> then(String next) {
        List<String> pathCopy = new ArrayList<>(this.paths);
        pathCopy.add(next);
        return new Path<>(pathCopy);
    }

    /**
     * Create from a path expression (fields separated by periods)
     * @param path The path expression
     * @param <TO> The starting object
     * @param <FROM> The ending object
     * @return A created Path
     */
    public static <TO, FROM> Path<TO, FROM> fromExpression(String path) {
        return new Path<>(Arrays.asList(path.split("\\.")));
    }

    /**
     * Create from a list of fields
     * @param paths The paths
     * @param <TO> The starting object
     * @param <FROM> The ending object
     * @return A created Path
     */
    public static <TO, FROM> Path<TO, FROM> fromFields(String... paths) {
        return new Path<>(Arrays.asList(paths));
    }

    /**
     * Get a path that just refers to the root (identity)
     * @param <TO> The object type
     * @return A path where resolution just returns itself
     */
    public static <TO> Path<TO, TO> root() {
        return (Path<TO, TO>) ROOT;
    }
}
