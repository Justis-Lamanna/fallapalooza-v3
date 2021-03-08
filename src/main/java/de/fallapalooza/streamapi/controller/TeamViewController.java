package de.fallapalooza.streamapi.controller;

import de.fallapalooza.streamapi.model.EpisodeLiveField;
import de.fallapalooza.streamapi.model.LiveField;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/view/team")
public class TeamViewController {
    @Autowired
    private TeamsService teamsService;

    @GetMapping("/display")
    public String getTeam(@RequestParam int firstDisplay, @RequestParam int secondDisplay, Model model) {
        List<Team> teams = teamsService.getTeamsByDisplay(firstDisplay, secondDisplay);
        model.addAttribute("display_one", firstDisplay);
        model.addAttribute("display_two", secondDisplay);
        model.addAttribute("teams", teams);
        return "team_display";
    }

    @GetMapping("/live/{display}")
    public String getLiveTeamField(@PathVariable int display, @RequestParam LiveField field, Model model) {
        model.addAttribute("display", display);
        model.addAttribute("field", field);
        return "live";
    }

    @GetMapping("/live/{display}/round/{round}/player/{player}/episode/{episode}")
    public String getLiveTeamScoreField(@PathVariable int display, @PathVariable int round, @PathVariable int player, @PathVariable int episode, Model model) {
        model.addAttribute("display", display);
        model.addAttribute("field", new EpisodeLiveField(round, player, episode));
        return "live";
    }

    @GetMapping("/live/{display}/round/current/player/{player}/episode/{episode}")
    public String getLiveTeamScoreField(@PathVariable int display, @PathVariable int player, @PathVariable int episode, Model model) {
        model.addAttribute("display", display);
        model.addAttribute("field", new EpisodeLiveField(player, episode));
        return "live";
    }

    @GetMapping("/ui")
    public String getUi() {
        return "updater";
    }
}
