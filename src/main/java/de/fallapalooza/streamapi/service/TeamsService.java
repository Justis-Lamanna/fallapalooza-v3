package de.fallapalooza.streamapi.service;

import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.retrieve.Path;
import de.fallapalooza.streamapi.annotation.retrieve.RetrieveService;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.model.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamsService {
    @Autowired
    private CellDefinition<Teams> definition;

    @Autowired
    private RetrieveService retrieveService;

    public String getTeamName(int teamNumber) {
        return retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "name"));
    }

    public String getTeamNameByDisplay(int displayNumber) {
        return getFieldByDisplay(displayNumber, Path.fromFields("name"));
    }

    public String getPlayerName(int teamNumber, int playerNum, boolean pronouns) {
        return retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "players", String.valueOf(playerNum), pronouns ? "nameWithPronouns" : "name"));
    }

    public String getPlayerNameByDisplay(int displayNumber, int playerNum, boolean pronouns) {
        return getFieldByDisplay(displayNumber, Path.fromFields("players", String.valueOf(playerNum), pronouns ? "nameWithPronouns" : "name"));
    }

    public Integer getScoreByRoundAndPlayerAndEpisode(int teamNumber, int roundNumber, int playerNum, int episodeNum) {
        return retrieveService.retrieve(definition,
                Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "scores", String.valueOf(playerNum), "scores", String.valueOf(episodeNum)));
    }

    public Integer getScoreByDisplayAndRoundAndPlayerAndEpisode(int displayNum, int roundNumber, int playerNum, int episodeNum) {
        return getFieldByDisplay(displayNum,
                Path.fromFields("rounds", String.valueOf(roundNumber), "scores", String.valueOf(playerNum), "scores", String.valueOf(episodeNum)));
    }

    public Integer getTotalByRound(int teamNumber, int roundNumber) {
        return retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "total"));
    }

    public Integer getTotalByDisplayAndRound(int displayNum, int roundNumber) {
        return getFieldByDisplay(displayNum, Path.fromFields("rounds", String.valueOf(roundNumber), "total"));
    }

    public String getNameByRound(int teamNumber, int roundNumber) {
        return retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "name"));
    }

    public String getNameByDisplayAndRound(int displayNum, int roundNumber) {
        return getFieldByDisplay(displayNum, Path.fromFields("rounds", String.valueOf(roundNumber), "name"));
    }

    public String getEpisodeByRound(int teamNumber, int roundNumber) {
        return retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "episode"));
    }

    public String getEpisodeByDisplayAndRound(int displayNum, int roundNumber) {
        return getFieldByDisplay(displayNum, Path.fromFields("rounds", String.valueOf(roundNumber), "episode"));
    }

    private <T> T getFieldByDisplay(int displayNumber, Path<Team, T> path) {
        return retrieveService.query(definition,
                Path.<Teams, List<Integer>>fromFields("teams", "*", "display"),
                display -> display != null && display == displayNumber,
                Path.<Teams, List<Team>>fromFields("teams", "*").then(path.wildcard()));
    }
}
