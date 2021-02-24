package de.fallapalooza.streamapi.annotation.exception;

import de.fallapalooza.streamapi.annotation.retrieve.Path;

public class IllegalPathException extends RuntimeException {
    public IllegalPathException(Path<?, ?> path) {
        super(String.join(".", path.getPaths()));
    }
}
