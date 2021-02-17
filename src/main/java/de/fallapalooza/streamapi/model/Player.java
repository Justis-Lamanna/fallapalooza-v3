package de.fallapalooza.streamapi.model;

import de.fallapalooza.streamapi.annotation.Cell;
import lombok.Data;

@Data
public class Player {
    @Cell(row = 0, col = 0)
    private String name;

    @Cell(row = 0, col = 1)
    private String nameWithPronouns;
}
