package de.fallapalooza.streamapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.function.Tuple3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class TeamsStateService {
    @Autowired
    private TeamsService teamsService;

    private Map<Tuple3<Integer, Integer, Integer>, Sinks.Many<Integer>> scoreSinks = Collections.synchronizedMap(new HashMap<>());

    public Flux<Integer> getScoreForDisplayAndCurrentRoundAndPlayerAndEpisode(int displayNum, int playerNum, int episodeNum) {
        return Flux.empty();
    }

    private <K, V> Sinks.Many<V> getLazily(Map<K, Sinks.Many<V>> map, K key, Supplier<V> firstIfNecessary) {
        return null;
    }
}
