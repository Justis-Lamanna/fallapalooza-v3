package de.fallapalooza.streamapi.model;

import de.fallapalooza.streamapi.annotation.Cell;
import de.fallapalooza.streamapi.annotation.Generator;
import de.fallapalooza.streamapi.annotation.Nested;
import lombok.Data;

import java.util.List;

@Data
public class Team {
    @Cell(row = 0, col = 1, sheet = "Scorecard")
    private String name;

    @Cell(row = 7, col = 3, sheet = "Scorecard")
    private Integer display;

    @Cell(row = 8, col = 3, sheet = "Scorecard")
    private Integer seed;

    @Nested(type = Player.class, length = 2, generator = @Generator(row = 2, rowOffset = 1, col = 1))
    private List<Player> players;

    @Nested(type = Round.class, length = 5, generator = @Generator(row = 2, col = 5, colOffset = 2))
    private List<Round> rounds;
}
