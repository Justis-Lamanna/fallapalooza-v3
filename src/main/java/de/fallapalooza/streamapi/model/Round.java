package de.fallapalooza.streamapi.model;

import de.fallapalooza.streamapi.annotation.Cell;
import de.fallapalooza.streamapi.annotation.Generator;
import de.fallapalooza.streamapi.annotation.Nested;
import de.fallapalooza.streamapi.annotation.Sheet;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

@Data
@Sheet("Scorecard")
public class Round {
    @Cell(row = 5, col = 0)
    private int total;

    @Cell(row = 6, col = 0)
    private String name;

    @Cell(row = 6, col = 1)
    private String episode;

    @Cell(row = 5, col = 1)
    //Possible statuses: TBP, BREAK, WIN, LOSS, DRAW, IP, NP
    private String status;

    @Nested(type = Scores.class, length = 2, generator = @Generator(row = 0, col = 0, colOffset = 1))
    private List<Scores> scores;

    public boolean isEmpty() {
        return StringUtils.equalsAnyIgnoreCase(status, "TBP", "NP");
    }

    public boolean isFull() {
        return StringUtils.equalsAnyIgnoreCase(status, "WIN", "LOSS", "BREAK");
    }

    public boolean isPartial() {
        return StringUtils.equalsAnyIgnoreCase(status, "IP", "DRAW");
    }

    @Data
    @Sheet("Scorecard")
    public static class Scores {
        @Nested(type = Integer.class, length = 5, generator = @Generator(row = 0, rowOffset = 1, col = 0))
        private List<Integer> scores;
    }
}
