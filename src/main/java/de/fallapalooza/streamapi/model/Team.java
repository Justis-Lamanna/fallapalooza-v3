package de.fallapalooza.streamapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.fallapalooza.streamapi.annotation.Cell;
import de.fallapalooza.streamapi.annotation.Generator;
import de.fallapalooza.streamapi.annotation.Nested;
import de.fallapalooza.streamapi.annotation.Sheet;
import lombok.Data;

import java.util.List;
import java.util.OptionalInt;

@Data
@Sheet("Scorecard")
public class Team {
    @Cell(row = 0, col = 1)
    private String name;

    @Cell(row = 7, col = 3)
    private Integer display;

    @Cell(row = 8, col = 3)
    private Integer seed;

    @Nested(type = Player.class, length = 2, generator = @Generator(row = 2, rowOffset = 1, col = 1))
    private List<Player> players;

    @Nested(type = Round.class, length = 5, generator = @Generator(row = 2, col = 5, colOffset = 2))
    private List<Round> rounds;

    @JsonIgnore
    private OptionalInt forcedCurrentRound = OptionalInt.empty();

    public Round getCurrentRound() {
        if(forcedCurrentRound.isPresent()) {
            return rounds.get(forcedCurrentRound.getAsInt());
        }
        for(int rNum = 0; rNum < rounds.size(); rNum++) {
            Round round = rounds.get(rNum);
            if(round.isPartial()) {
                return round;
            }

            if(round.isEmpty()) {
                if(rNum == 0) {
                    return round;
                } else {
                    return rounds.get(rNum - 1);
                }
            }
        }
        return rounds.get(rounds.size() - 1);
    }
}
