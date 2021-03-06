package de.fallapalooza.streamapi.controller;

import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamsService teamsService;

    @GetMapping("/{teamNum}")
    public Team getNameByTeamNumber(@PathVariable int teamNum) {
        return teamsService.getTeam(teamNum);
    }

    @GetMapping("/display/{displayNum}")
    public Team getNameByDisplayNumber(@PathVariable int displayNum) {
        return teamsService.getTeamByDisplay(displayNum);
    }
}
