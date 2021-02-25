package de.fallapalooza.streamapi.model;

import de.fallapalooza.streamapi.annotation.Cell;
import de.fallapalooza.streamapi.annotation.Generator;
import de.fallapalooza.streamapi.annotation.Nested;
import de.fallapalooza.streamapi.annotation.Sheet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Sheet("Bracket")
public class Bracket {
    @Nested(type = RoundOneMatchup.class, length = 16, generator = @Generator(row = 3, rowOffset = 4, col = 2))
    private List<Matchup> roundOne;

    @Nested(type = RoundTwoMatchup.class, length = 8, generator = @Generator(row = 4, rowOffset = 8, col = 5))
    private List<Matchup> roundTwo;

    @Nested(type = RoundThreeMatchup.class, length = 4, generator = @Generator(row = 6, rowOffset = 16, col = 8))
    private List<Matchup> quarterfinals;

    @Nested(type = RoundFourMatchup.class, length = 2, generator = @Generator(row = 10, rowOffset = 32, col = 11))
    private List<Matchup> semifinals;

    @Cell(row = 60, col = 14)
    private RoundFiveLoserMatchup thirdPlaceMatch;

    @Cell(row = 18, col = 14)
    private RoundFiveWinnerMatchup finals;

    @Cell(row = 33, col = 17)
    private String firstPlace;

    @Cell(row = 35, col = 17)
    private String secondPlace;

    @Cell(row = 61, col = 17)
    private String thirdPlace;

    @Cell(row = 63, col = 17)
    private String fourthPlace;

    @Data
    @Sheet("Bracket")
    public static class RoundOneMatchup implements Matchup {
        @Cell(row = 0, col=0) String teamOne;
        @Cell(row = 2, col=0) String teamTwo;
    }

    @Data
    @Sheet("Bracket")
    public static class RoundTwoMatchup implements Matchup {
        @Cell(row = 0, col=0) String teamOne;
        @Cell(row = 4, col=0) String teamTwo;
    }

    @Data
    @Sheet("Bracket")
    public static class RoundThreeMatchup implements Matchup {
        @Cell(row = 0, col=0) String teamOne;
        @Cell(row = 8, col=0) String teamTwo;
    }

    @Data
    @Sheet("Bracket")
    public static class RoundFourMatchup implements Matchup {
        @Cell(row = 0, col=0) String teamOne;
        @Cell(row = 16, col=0) String teamTwo;
    }

    @Data
    @Sheet("Bracket")
    public static class RoundFiveWinnerMatchup implements Matchup {
        @Cell(row = 0, col=0) String teamOne;
        @Cell(row = 32, col=0) String teamTwo;
    }

    @Data
    @Sheet("Bracket")
    public static class RoundFiveLoserMatchup implements Matchup {
        @Cell(row = 0, col=0) String teamOne;
        @Cell(row = 4, col=0) String teamTwo;
    }
}
