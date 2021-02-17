package de.fallapalooza.streamapi.model;

import de.fallapalooza.streamapi.annotation.Cell;
import de.fallapalooza.streamapi.annotation.Generator;
import de.fallapalooza.streamapi.annotation.Nested;
import lombok.Data;

import java.util.List;

@Data
public class Round {
    @Cell(row = 5, col = 0)
    private int total;

    @Cell(row = 6, col = 0)
    private String name;

    @Cell(row = 6, col = 1)
    private String episode;

    @Nested(type = Scores.class, length = 2, generator = @Generator(row = 0, col = 0, colOffset = 1))
    private List<Scores> scores;

    @Data
    public static class Scores {
        @Nested(type = Integer.class, length = 5, generator = @Generator(row = 0, rowOffset = 1, col = 0))
        private List<Integer> scores;
    }
}
