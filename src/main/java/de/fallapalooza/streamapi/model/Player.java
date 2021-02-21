package de.fallapalooza.streamapi.model;

import de.fallapalooza.streamapi.annotation.Cell;
import lombok.Data;

@Data
public class Player {
    @Cell(row = 0, col = 0, sheet = "Scorecard")
    private String name;

    @Cell(row = 0, col = 1, sheet = "Scorecard")
    private String nameWithPronouns;
}
