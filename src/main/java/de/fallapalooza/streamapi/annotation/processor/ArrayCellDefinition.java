package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.function.IndexToOriginGenerator;
import de.fallapalooza.streamapi.annotation.model.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A resolver which resolves a fixed-size set of objects by moving each origin
 */
@RequiredArgsConstructor
@Getter
public class ArrayCellDefinition<T, C> implements CellDefinition<C> {
    private final CellDefinition<T> subResolver;
    private final int size;
    private final IndexToOriginGenerator generator;
    private final Collector<T, ?, C> collector;

    @Override
    public List<String> resolveCell(Point origin) {
        return IntStream.range(0, size)
                .mapToObj(generator::getOrigin)
                .map(origin::add)
                .flatMap(gOrigin -> subResolver.resolveCell(gOrigin).stream())
                .collect(Collectors.toList());
    }

    @Override
    public C convertValue(Iterator<ValueRange> values) {
        return IntStream.range(0, size)
                .mapToObj(idx -> subResolver.convertValue(values))
                .collect(collector);
    }

    @Override
    public CellDefinition<?> getDefinitionForField(String name) {
        if("*".equals(name)) {
            return new WildcardArrayCellDefinition<>(subResolver, size, generator, collector);
        }
        try {
            int idx = Integer.parseInt(name);
            if(idx < 0) {
                throw new IllegalArgumentException("Unable to parse field " + name + " - expected non-negative number");
            }

            Point offset = generator.getOrigin(idx);
            return new OffsetCellDefinitionWrapper<>(subResolver, offset);

        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public Set<String> getFields() {
        return IntStream.range(0, size)
                .mapToObj(Integer::toString)
                .collect(Collectors.toSet());
    }
}
