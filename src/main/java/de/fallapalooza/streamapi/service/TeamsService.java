package de.fallapalooza.streamapi.service;

import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.retrieve.Path;
import de.fallapalooza.streamapi.annotation.retrieve.RetrieveService;
import de.fallapalooza.streamapi.model.Round;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.model.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Integer getScoreByCurrentRoundAndPlayerAndEpisode(int teamNumber, int playerNum, int episodeNum) {
        List<Round> rounds = retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds"));
        return findCurrentRound(rounds).getScores().get(playerNum).getScores().get(episodeNum);
    }

    public List<Integer> getScoresByRoundAndPlayer(int teamNumber, int roundNumber, int playerNum) {
        return retrieveService.retrieve(definition,
                Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "scores", String.valueOf(playerNum), "scores"));
    }

    public List<Integer> getScoresByCurrentRoundAndPlayer(int teamNumber, int playerNum) {
        List<Round> rounds = retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds"));
        return findCurrentRound(rounds).getScores().get(playerNum).getScores();
    }

    public List<List<Integer>> getScoresByRound(int teamNumber, int roundNumber) {
        return retrieveService.retrieve(definition,
                Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "scores", "*", "scores"));
    }

    public List<List<Integer>> getScoresByCurrentRound(int teamNumber) {
        List<Round> rounds = retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds"));
        return findCurrentRound(rounds).getScores().stream()
                .map(Round.Scores::getScores)
                .collect(Collectors.toList());
    }

    public Integer getScoreByDisplayAndRoundAndPlayerAndEpisode(int displayNum, int roundNumber, int playerNum, int episodeNum) {
        return getFieldByDisplay(displayNum,
                Path.fromFields("rounds", String.valueOf(roundNumber), "scores", String.valueOf(playerNum), "scores", String.valueOf(episodeNum)));
    }

    public Integer getScoreByDisplayAndCurrentRoundAndPlayerAndEpisode(int displayNum, int playerNum, int episodeNum) {
        List<Round> rounds = getFieldByDisplay(displayNum,
                Path.fromFields("rounds"));
        return findCurrentRound(rounds).getScores().get(playerNum).getScores().get(episodeNum);
    }

    public List<Integer> getScoresByDisplayAndRoundAndPlayer(int displayNum, int roundNumber, int playerNum) {
        return getFieldByDisplay(displayNum,
                Path.fromFields("rounds", String.valueOf(roundNumber), "scores", String.valueOf(playerNum), "scores"));
    }

    public List<Integer> getScoresByDisplayAndCurrentRoundAndPlayer(int displayNum, int playerNum) {
        List<Round> rounds = getFieldByDisplay(displayNum,
                Path.fromFields("rounds"));
        return findCurrentRound(rounds).getScores().get(playerNum).getScores();
    }

    public Integer getTotalByRound(int teamNumber, int roundNumber) {
        return retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "total"));
    }

    public Integer getTotalByCurrentRound(int teamNumber) {
        List<Round> rounds = retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds"));
        return findCurrentRound(rounds).getTotal();
    }

    public Integer getTotalByDisplayAndRound(int displayNum, int roundNumber) {
        return getFieldByDisplay(displayNum, Path.fromFields("rounds", String.valueOf(roundNumber), "total"));
    }

    public Integer getTotalByDisplayAndCurrentRound(int displayNum) {
        List<Round> rounds = getFieldByDisplay(displayNum,
                Path.fromFields("rounds"));
        return findCurrentRound(rounds).getTotal();
    }

    public String getNameByRound(int teamNumber, int roundNumber) {
        return retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "name"));
    }

    public String getNameByCurrentRound(int teamNumber) {
        List<Round> rounds = retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds"));
        return findCurrentRound(rounds).getName();
    }

    public String getNameByDisplayAndRound(int displayNum, int roundNumber) {
        return getFieldByDisplay(displayNum, Path.fromFields("rounds", String.valueOf(roundNumber), "name"));
    }

    public String getNameByDisplayAndCurrentRound(int displayNum) {
        List<Round> rounds = getFieldByDisplay(displayNum,
                Path.fromFields("rounds"));
        return findCurrentRound(rounds).getName();
    }

    public String getEpisodeByRound(int teamNumber, int roundNumber) {
        return retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds", String.valueOf(roundNumber), "episode"));
    }

    public String getEpisodeByCurrentRound(int teamNumber) {
        List<Round> rounds = retrieveService.retrieve(definition, Path.fromFields("teams", String.valueOf(teamNumber), "rounds"));
        return findCurrentRound(rounds).getEpisode();
    }

    public String getEpisodeByDisplayAndRound(int displayNum, int roundNumber) {
        return getFieldByDisplay(displayNum, Path.fromFields("rounds", String.valueOf(roundNumber), "episode"));
    }

    public String getEpisodeByDisplayAndCurrentRound(int displayNum) {
        List<Round> rounds = getFieldByDisplay(displayNum,
                Path.fromFields("rounds"));
        return findCurrentRound(rounds).getEpisode();
    }

    private <T> T getFieldByDisplay(int displayNumber, Path<Team, T> path) {
        return retrieveService.query(definition,
                Path.<Teams, List<Integer>>fromFields("teams", "*", "display"),
                display -> display != null && display == displayNumber,
                Path.<Teams, List<Team>>fromFields("teams", "*").then(path.wildcard()));
    }

    private Round findCurrentRound(List<Round> rounds) {
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
        return rounds.get(0);
    }
}
