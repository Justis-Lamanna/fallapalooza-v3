package de.fallapalooza.streamapi.controller;

import de.fallapalooza.streamapi.model.Round;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.service.TeamsStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream/team")
public class TeamStateController {
    @Autowired
    private TeamsStateService service;

    @GetMapping("/display/{displayNum}/name")
    public Flux<ServerSentEvent<String>> getTeamName(@PathVariable int displayNum) {
        return service.getTeamForDisplay(displayNum)
                .map(Team::getName)
                .map(team -> ServerSentEvent.<String>builder()
                        .id("display-" + displayNum + "-name")
                        .event("team-event")
                        .data(team)
                        .build());
    }

    @GetMapping("/display/{displayNum}/player/{playerNum}/name")
    public Flux<ServerSentEvent<String>> getPlayerName(@PathVariable int displayNum, @PathVariable int playerNum,
                                                       @RequestParam(defaultValue = "false") boolean pronouns) {
        return service.getTeamForDisplay(displayNum)
                .map(team -> team.getPlayers().get(playerNum))
                .map(player -> ServerSentEvent.<String>builder()
                        .id("display-" + displayNum + "-player-" + playerNum + "-name-" + pronouns)
                        .event("team-event")
                        .data(pronouns ? player.getNameWithPronouns() : player.getName())
                        .build());
    }

    @PostMapping("/display/{displayNum}")
    public ResponseEntity<Void> refreshTeam(@PathVariable int displayNum) {
        service.refreshTeamForDisplay(displayNum);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/display/{displayNum}/round/current")
    public Flux<ServerSentEvent<Round>> getRound(@PathVariable int displayNum) {
        return service.getCurrentRoundForDisplay(displayNum)
                .map(round -> ServerSentEvent.<Round>builder()
                        .id("display-" + displayNum + "-current-round")
                        .event("current-round-event")
                        .data(round)
                        .build());
    }

    @PostMapping("/display/{displayNum}/round/current")
    public ResponseEntity<Void> refreshRound(@PathVariable int displayNum) {
        service.refreshCurrentRoundForDisplay(displayNum);
        return ResponseEntity.accepted().build();
    }
}
