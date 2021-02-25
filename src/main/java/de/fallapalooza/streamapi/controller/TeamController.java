package de.fallapalooza.streamapi.controller;

import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.retrieve.Path;
import de.fallapalooza.streamapi.annotation.retrieve.RetrieveService;
import de.fallapalooza.streamapi.model.Player;
import de.fallapalooza.streamapi.model.Round;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.model.Teams;
import de.fallapalooza.streamapi.service.DisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/team")
public class TeamController {
    private static final Path<Teams, List<Team>> TEAMS = Path.fromFields("teams");
    private static final Path<Team, String> TEAM_NAME = Path.fromFields("name");
    private static final Path<Team, List<Player>> PLAYERS = Path.fromFields("players");
    private static final Path<Team, List<Round>> ROUNDS = Path.fromFields("rounds");

    @Autowired
    private CellDefinition<Teams> definition;

    @Autowired
    private RetrieveService retrieveService;

    @Autowired
    private DisplayService displayService;

    @GetMapping("/{number}/name")
    public String getTeamName(@PathVariable int number, Model model) {
        String name = retrieveService.retrieve(definition, Path.fromFields("teams", Integer.toString(number), "name"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/{number}/player/{playerNumber}/name")
    public String getPlayerName(@PathVariable int number, @PathVariable int playerNumber, Model model) {
        String name = retrieveService.retrieve(definition, Path.fromFields("teams", Integer.toString(number), "players", Integer.toString(playerNumber), "name"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/{number}/player/{playerNumber}/pronouns")
    public String getPlayerNamePronouns(@PathVariable int number, @PathVariable int playerNumber, Model model) {
        String name = retrieveService.retrieve(definition, Path.fromFields("teams", Integer.toString(number), "players", Integer.toString(playerNumber), "nameWithPronouns"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/{number}/round/{roundNumber}/name")
    public String getRoundName(@PathVariable int number, @PathVariable int roundNumber, Model model) {
        String name = retrieveService.retrieve(definition, Path.fromFields("teams", Integer.toString(number), "rounds", Integer.toString(roundNumber), "name"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/{number}/round/{roundNumber}/total")
    public String getRoundTotal(@PathVariable int number, @PathVariable int roundNumber, Model model) {
        int total = retrieveService.retrieve(definition, Path.fromFields("teams", Integer.toString(number), "rounds", Integer.toString(roundNumber), "total"));
        if(total != 0) {
            model.addAttribute("value", total);
        }
        return "value";
    }

    @GetMapping("/{number}/round/{roundNumber}/episode")
    public String getEpisodeName(@PathVariable int number, @PathVariable int roundNumber, Model model) {
        String name = retrieveService.retrieve(definition, Path.fromFields("teams", Integer.toString(number), "rounds", Integer.toString(roundNumber), "episode"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/{number}/round/{roundNumber}/episode/{episodeNumber}/player/{playerNumber}/score")
    public String getEpisodeName(@PathVariable int number, @PathVariable int roundNumber, @PathVariable int episodeNumber, @PathVariable int playernumber, Model model) {
        String name = retrieveService.retrieve(definition, Path.fromFields("teams", Integer.toString(number), "rounds", Integer.toString(roundNumber), "scores", Integer.toString(episodeNumber), "scores", Integer.toString(playernumber)));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/display/{number}/name")
    public String getTeamNameByDisplay(@PathVariable int number, Model model) {
        String name = displayService.getValueForDisplayNumber(number, TEAM_NAME);
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/display/{number}/player/{playerNumber}/name")
    public String getPlayerNameByDisplay(@PathVariable int number, @PathVariable int playerNumber, Model model) {
        String name = displayService.getValueForDisplayNumber(number, PLAYERS.then(Integer.toString(playerNumber), "name"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/display/{number}/player/{playerNumber}/pronouns")
    public String getPlayerNamePronounsByDisplay(@PathVariable int number, @PathVariable int playerNumber, Model model) {
        String name = displayService.getValueForDisplayNumber(number, PLAYERS.then(Integer.toString(playerNumber), "nameWithPronouns"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/display/{number}/round/{roundNumber}/name")
    public String getRoundNameByDisplay(@PathVariable int number, @PathVariable int roundNumber, Model model) {
        String name = displayService.getValueForDisplayNumber(number, ROUNDS.then(Integer.toString(roundNumber), "name"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/display/{number}/round/{roundNumber}/total")
    public String getRoundTotalByDisplay(@PathVariable int number, @PathVariable int roundNumber, Model model) {
        int total = displayService.getValueForDisplayNumber(number, ROUNDS.then(Integer.toString(roundNumber), "total"));
        if(total != 0) {
            model.addAttribute("value", total);
        }
        return "value";
    }

    @GetMapping("/display/{number}/round/{roundNumber}/episode")
    public String getEpisodeNameByDisplay(@PathVariable int number, @PathVariable int roundNumber, Model model) {
        String name = displayService.getValueForDisplayNumber(number, ROUNDS.then(Integer.toString(roundNumber), "episode"));
        model.addAttribute("value", name);
        return "value";
    }

    @GetMapping("/display/{number}/round/{roundNumber}/episode/{episodeNumber}/player/{playerNumber}/score")
    public String getEpisodeNameByDisplay(@PathVariable int number, @PathVariable int roundNumber, @PathVariable int episodeNumber, @PathVariable int playernumber, Model model) {
        String name = displayService.getValueForDisplayNumber(number, ROUNDS.then(Integer.toString(roundNumber), "scores", Integer.toString(episodeNumber), "scores", Integer.toString(playernumber)));
        model.addAttribute("value", name);
        return "value";
    }
}
