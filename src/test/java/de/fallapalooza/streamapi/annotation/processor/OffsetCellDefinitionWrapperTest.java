package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.model.Point;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OffsetCellDefinitionWrapperTest {
    @Test
    void shouldOffsetOriginBeforeResolving() {
        CellDefinition<Integer> testWrapped = Mockito.spy(new ConstantCellDefinition<>(1));
        OffsetCellDefinitionWrapper<Integer> offset = new OffsetCellDefinitionWrapper<>(testWrapped, new Point(4, 4));

        List<String> cells = offset.resolveCell(Point.ONE);

        assertIterableEquals(Collections.singletonList("E5"), cells);
    }
}