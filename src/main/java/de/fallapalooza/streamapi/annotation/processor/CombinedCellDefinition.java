package de.fallapalooza.streamapi.annotation.processor;

import com.google.api.services.sheets.v4.model.ValueRange;
import de.fallapalooza.streamapi.annotation.model.Point;
import lombok.Data;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A basic CellDefinition which delegates to each of its parts
 * @param <T> The type created by this definition
 */
@Data
public class CombinedCellDefinition<T> implements CellDefinition<T> {
    private final CellResolver resolver;
    private final ObjectResolver<T> objectResolver;

    @Override
    public List<String> resolveCell(Point origin) {
        return resolver.resolveCell(origin);
    }

    @Override
    public T convertValue(Iterator<ValueRange> values) {
        return objectResolver.convertValue(values);
    }

    @Override
    public CellDefinition<?> getDefinitionForField(String name) {
        if(resolver instanceof NestedCellResolver) {
            try {
                int idx = Integer.parseInt(name);
                if(idx < 0) {
                    throw new IllegalArgumentException("Unable to parse path " + name + ", expected positive number");
                }

                NestedCellResolver ncr = (NestedCellResolver) resolver;
                return new CombinedCellDefinition<>(ncr.getResolverForIndex(idx), objectResolver);

            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Unable to parse path " + name + ", expected number");
            }
        }

        if(resolver instanceof SingleCellResolver) {
            return this;
        }

        return null;
    }

    @Override
    public Set<String> getFields() {
        if(resolver instanceof NestedCellResolver) {
            NestedCellResolver ncr = (NestedCellResolver) resolver;
            return IntStream.range(0, ncr.getSize())
                    .mapToObj(Integer::toString)
                    .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }
}
