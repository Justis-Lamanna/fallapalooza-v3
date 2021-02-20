package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.function.IndexToOriginGenerator;
import de.fallapalooza.streamapi.annotation.model.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A resolver which resolves a fixed-size set of objects by moving each origin
 */
@RequiredArgsConstructor
@Getter
public class NestedCellResolver implements CellResolver {
    private final CellResolver resolver;
    private final int size;
    private final IndexToOriginGenerator generator;

    @Override
    public List<String> resolveCell(Point origin) {
        return IntStream.range(0, size)
                .mapToObj(generator::getOrigin)
                .map(origin::add)
                .flatMap(gOrigin -> resolver.resolveCell(gOrigin).stream())
                .collect(Collectors.toList());
    }
}
