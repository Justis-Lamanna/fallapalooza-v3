package de.fallapalooza.streamapi.controller;

import de.fallapalooza.streamapi.model.RefreshTeamPayload;
import de.fallapalooza.streamapi.model.Round;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.service.TeamsStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.OptionalInt;

@RestController
@RequestMapping("/stream/team/display/{displayNum}")
public class TeamStateController {
    @Autowired
    private TeamsStateService service;

    @GetMapping("/name")
    public Flux<ServerSentEvent<String>> getTeamName(@PathVariable int displayNum) {
        return service.getTeamForDisplay(displayNum)
                .map(Team::getName)
                .map(name -> ServerSentEvent.<String>builder()
                        .id(String.format("display-%d-name", displayNum))
                        .event("team-event")
                        .data(name)
                        .build());
    }

    @GetMapping("/player/{playerNum}/name")
    public Flux<ServerSentEvent<String>> getPlayerName(@PathVariable int displayNum, @PathVariable int playerNum, @RequestParam(required = false) boolean pronouns) {
        return service.getTeamForDisplay(displayNum)
                .map(team -> team.getPlayers().get(playerNum))
                .map(player -> pronouns ? player.getNameWithPronouns() : player.getName())
                .map(name -> ServerSentEvent.<String>builder()
                        .id(String.format("display-%d-player-%d-name-%s", displayNum, playerNum, pronouns))
                        .event("team-event")
                        .data(name)
                        .build());
    }

    @GetMapping("/round/{roundNum}/player/{playerNum}/episode/{epNum}")
    public Flux<ServerSentEvent<String>> getScore(@PathVariable int displayNum, @PathVariable int roundNum, @PathVariable int playerNum, @PathVariable int epNum) {
        return service.getTeamForDisplay(displayNum)
                .map(team -> team.getRounds().get(roundNum))
                .map(round -> round.getScores().get(playerNum))
                .map(scores -> scores.getScores().get(epNum))
                .map(TeamStateController::toStringBlankIfZero)
                .map(score -> ServerSentEvent.<String>builder()
                        .id(String.format("display-%d-round-%d-player-%d-episode-%d", displayNum, roundNum, playerNum, epNum))
                        .event("team-event")
                        .data(score)
                        .build());
    }

    @GetMapping("/round/current/player/{playerNum}/episode/{epNum}")
    public Flux<ServerSentEvent<String>> getScore(@PathVariable int displayNum, @PathVariable int playerNum, @PathVariable int epNum) {
        return service.getTeamForDisplay(displayNum)
                .map(Team::getCurrentRound)
                .map(round -> round.getScores().get(playerNum))
                .map(scores -> scores.getScores().get(epNum))
                .map(TeamStateController::toStringBlankIfZero)
                .map(score -> ServerSentEvent.<String>builder()
                        .id(String.format("display-%d-round-current-player-%d-episode-%d", displayNum, playerNum, epNum))
                        .event("team-event")
                        .data(score)
                        .build());
    }

    @GetMapping("/round/{roundNum}/total")
    public Flux<ServerSentEvent<String>> getTotal(@PathVariable int displayNum, @PathVariable int roundNum) {
        return service.getTeamForDisplay(displayNum)
                .map(team -> team.getRounds().get(roundNum))
                .map(Round::getTotal)
                .map(TeamStateController::toStringBlankIfZero)
                .map(score -> ServerSentEvent.<String>builder()
                        .id(String.format("display-%d-round-%d-total", displayNum, roundNum))
                        .event("team-event")
                        .data(score)
                        .build());
    }

    @GetMapping("/round/current/total")
    public Flux<ServerSentEvent<String>> getCurrentTotal(@PathVariable int displayNum) {
        return service.getTeamForDisplay(displayNum)
                .map(Team::getCurrentRound)
                .map(Round::getTotal)
                .map(TeamStateController::toStringBlankIfZero)
                .map(score -> ServerSentEvent.<String>builder()
                        .id(String.format("display-%d-round-current-total", displayNum))
                        .event("team-event")
                        .data(score)
                        .build());
    }

    @GetMapping("/round/current/episode-badge")
    public Flux<ServerSentEvent<String>> getCurrentEpisodeBadge(@PathVariable int displayNum) {
        return service.getTeamForDisplay(displayNum)
                .map(Team::getCurrentRound)
                .map(Round::getEpisode)
                .map(score -> ServerSentEvent.<String>builder()
                        .id(String.format("display-%d-round-current-episode-badge", displayNum))
                        .event("team-event")
                        .data(score)
                        .build());
    }

    @GetMapping("/round/current/round-badge")
    public Flux<ServerSentEvent<String>> getCurrentRoundBadge(@PathVariable int displayNum) {
        return service.getTeamForDisplay(displayNum)
                .map(Team::getCurrentRound)
                .map(Round::getName)
                .map(score -> ServerSentEvent.<String>builder()
                        .id(String.format("display-%d-round-current-round-badge", displayNum))
                        .event("team-event")
                        .data(score)
                        .build());
    }

    @PostMapping
    public ResponseEntity<Void> refreshTeam(@PathVariable int displayNum, @RequestBody RefreshTeamPayload payload) {
        service.refreshTeamForDisplay(displayNum, payload.getForcedRound() < 0 ? OptionalInt.empty() : OptionalInt.of(payload.getForcedRound()));
        return ResponseEntity.accepted().build();
    }

    private static String toStringBlankIfZero(int value) {
        return value == 0 ? "" : String.valueOf(value);
    }
}
