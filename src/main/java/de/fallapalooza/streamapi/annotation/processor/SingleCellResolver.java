package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.model.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * A basic CellResolver which simply resolves to a single relative point
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SingleCellResolver implements CellResolver {
    private final Point relativePoint;
    private String sheet;

    @Override
    public List<String> resolveCell(Point origin) {
        Point calculated = origin.add(relativePoint);
        return Collections.singletonList(sheet == null ? calculated.toExcel() : calculated.toExcelWithSheet(sheet));
    }
}
