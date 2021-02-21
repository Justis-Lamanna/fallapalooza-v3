package de.fallapalooza.streamapi.annotation.retrieve;

import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;

public interface RetrieveService {
    default <T> T retrieve(CellDefinition<T> definition) {
        return retrieve(definition, Point.ZERO);
    }

    <T> T retrieve(CellDefinition<T> definition, Point origin);
}
