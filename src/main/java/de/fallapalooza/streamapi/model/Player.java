package de.fallapalooza.streamapi.model;

import de.fallapalooza.streamapi.annotation.Cell;
import de.fallapalooza.streamapi.annotation.Sheet;
import lombok.Data;

@Data
@Sheet("Scorecard")
public class Player {
    @Cell(row = 0, col = 0)
    private String name;

    @Cell(row = 0, col = 1)
    private String nameWithPronouns;
}
