package de.fallapalooza.streamapi.controller;

import de.fallapalooza.streamapi.annotation.retrieve.Path;
import de.fallapalooza.streamapi.model.Player;
import de.fallapalooza.streamapi.model.Round;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.model.Teams;
import de.fallapalooza.streamapi.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    private static final Path<Teams, List<Team>> TEAMS = Path.fromFields("teams");
    private static final Path<Team, String> TEAM_NAME = Path.fromFields("name");
    private static final Path<Team, List<Player>> PLAYERS = Path.fromFields("players");
    private static final Path<Team, List<Round>> ROUNDS = Path.fromFields("rounds");

    @Autowired
    private TeamsService teamsService;

    @GetMapping("/{teamNum}/name")
    public String getNameByTeamNumber(@PathVariable int teamNum) {
        return teamsService.getTeamName(teamNum);
    }

    @GetMapping("/display/{displayNum}/name")
    public String getNameByDisplayNumber(@PathVariable int displayNum) {
        return teamsService.getTeamNameByDisplay(displayNum);
    }

    @GetMapping("/{teamNum}/player/{playerNum}/name")
    public String getPlayerNameByTeamAndPlayerNumber(@PathVariable int teamNum, @PathVariable int playerNum, @RequestParam(defaultValue = "false") boolean pronouns) {
        return teamsService.getPlayerName(teamNum, playerNum, pronouns);
    }

    @GetMapping("/display/{displayNum}/player/{playerNum}/name")
    public String getPlayerNameByDisplayAndPlayerNumber(@PathVariable int displayNum, @PathVariable int playerNum, @RequestParam(defaultValue = "false") boolean pronouns) {
        return teamsService.getPlayerNameByDisplay(displayNum, playerNum, pronouns);
    }

    @GetMapping("/{teamNum}/round/{roundNum}/player/{playerNum}/episode/{episodeNum}")
    public Integer getScoreByTeamAndRoundAndPlayerAndEpisodeNumber(@PathVariable int teamNum, @PathVariable int roundNum, @PathVariable int playerNum, @PathVariable int episodeNum) {
        return teamsService.getScoreByRoundAndPlayerAndEpisode(teamNum, roundNum, playerNum, episodeNum);
    }

    @GetMapping("/display/{displayNum}/round/{roundNum}/player/{playerNum}/episode/{episodeNum}")
    public Integer getScoreByDisplayNumAndRoundAndPlayerAndEpisodeNumber(@PathVariable int displayNum, @PathVariable int roundNum, @PathVariable int playerNum, @PathVariable int episodeNum) {
        return teamsService.getScoreByDisplayAndRoundAndPlayerAndEpisode(displayNum, roundNum, playerNum, episodeNum);
    }

    @GetMapping("/{teamNum}/round/{roundNum}/total")
    public Integer getTotalByTeamAndRound(@PathVariable int teamNum, @PathVariable int roundNum) {
        return teamsService.getTotalByRound(teamNum, roundNum);
    }

    @GetMapping("/display/{displayNum}/round/{roundNum}/total")
    public Integer getTotalByDisplayAndRound(@PathVariable int displayNum, @PathVariable int roundNum) {
        return teamsService.getTotalByDisplayAndRound(displayNum, roundNum);
    }

    @GetMapping("/{teamNum}/round/{roundNum}/total")
    public String getNameByTeamAndRound(@PathVariable int teamNum, @PathVariable int roundNum) {
        return teamsService.getNameByRound(teamNum, roundNum);
    }

    @GetMapping("/display/{displayNum}/round/{roundNum}/name")
    public String getNameByDisplayAndRound(@PathVariable int displayNum, @PathVariable int roundNum) {
        return teamsService.getNameByDisplayAndRound(displayNum, roundNum);
    }

    @GetMapping("/{teamNum}/round/{roundNum}/episode")
    public String getEpisodeByTeamAndRound(@PathVariable int teamNum, @PathVariable int roundNum) {
        return teamsService.getEpisodeByRound(teamNum, roundNum);
    }

    @GetMapping("/display/{displayNum}/round/{roundNum}/episode")
    public String getEpisodeByDisplayAndRound(@PathVariable int displayNum, @PathVariable int roundNum) {
        return teamsService.getEpisodeByDisplayAndRound(displayNum, roundNum);
    }
}
