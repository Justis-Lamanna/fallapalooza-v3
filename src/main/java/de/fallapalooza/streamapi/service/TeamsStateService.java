package de.fallapalooza.streamapi.service;

import de.fallapalooza.streamapi.model.Round;
import de.fallapalooza.streamapi.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Supplier;

@Service
public class TeamsStateService {
    @Autowired
    private TeamsService teamsService;

    private final Map<Integer, Sinks.Many<Team>> teamSinks = new HashMap<>();
    private final Map<Integer, Sinks.Many<Round>> currentRoundsSinks = new HashMap<>();

    public Flux<Team> getTeamForDisplay(int displayNum) {
        return getLazily(teamSinks, displayNum, () -> teamsService.getTeamByDisplay(displayNum))
                .asFlux();
    }

    public void refreshTeamForDisplay(int displayNum) {
        refreshTeamForDisplay(displayNum, OptionalInt.empty());
    }

    public void refreshTeamForDisplay(int displayNum, OptionalInt forcedRound) {
        setLazily(teamSinks, displayNum, () -> {
            Team team = teamsService.getTeamByDisplay(displayNum);
            if(team != null) {
                team.setForcedCurrentRound(forcedRound);
            }
            return team;
        });
    }

    private <K, V> Sinks.Many<V> getLazily(Map<K, Sinks.Many<V>> map, K key, Supplier<V> firstIfNecessary) {
        if(map.containsKey(key)) {
            return map.get(key);
        }
        Sinks.Many<V> created = Sinks.many().replay().latest();
        map.put(key, created);
        V firstObj = firstIfNecessary.get();
        if(firstObj != null) {
            created.emitNext(firstObj, Sinks.EmitFailureHandler.FAIL_FAST);
        }
        return created;
    }

    private <K, V> void setLazily(Map<K, Sinks.Many<V>> map, K key, Supplier<V> valueSupplier) {
        if(map.containsKey(key)) {
            V nextObj = valueSupplier.get();
            if(nextObj != null) {
                map.get(key).emitNext(nextObj, Sinks.EmitFailureHandler.FAIL_FAST);
            }
        } else {
            Sinks.Many<V> created = Sinks.many().replay().latest();
            map.put(key, created);
            V nextObj = valueSupplier.get();
            if(nextObj != null) {
                created.emitNext(nextObj, Sinks.EmitFailureHandler.FAIL_FAST);
            }
        }
    }
}
