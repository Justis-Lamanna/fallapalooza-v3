package de.fallapalooza.streamapi.annotation.processor;

import de.fallapalooza.streamapi.annotation.model.Point;

import java.util.Collections;
import java.util.List;

/**
 * A formula which calculates the actual cell values, given some origin.
 */
public interface CellResolver {
    CellResolver IDENTITY = (origin) -> Collections.singletonList(origin.toExcel());

    /**
     * Identity function to allow for specifying a sheet
     * @param sheet The sheet to specify
     * @return A CellResolver which returns relative to sheet name
     */
    static CellResolver identity(String sheet) {
        return (origin) -> Collections.singletonList(origin.toExcelWithSheet(sheet));
    }

    /**
     * Calculate one or more cells that correspond to the constructed object
     * @param origin The origin to calculate relative to
     * @return A list of cells corresponding to this constructed object
     */
    List<String> resolveCell(Point origin);
}
