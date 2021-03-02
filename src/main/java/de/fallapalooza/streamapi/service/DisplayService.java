package de.fallapalooza.streamapi.service;

import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.retrieve.Path;
import de.fallapalooza.streamapi.annotation.retrieve.RetrieveService;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.model.Teams;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Service
public class DisplayService {
    private static final Path<Teams, List<Team>> PATH_TO_ALL_TEAMS
            = Path.fromFields("teams", "*");
    private static final Path<Teams, List<Integer>> PATH_TO_DISPLAY
            = PATH_TO_ALL_TEAMS.then("display");

    @Autowired
    private CellDefinition<Teams> definition;

    @Autowired
    private RetrieveService retrieveService;

    public Path<Teams, Team> getIndexForDisplayNumber(int displayNumber) {
        List<Integer> displayNumbers = retrieveService.retrieve(definition, PATH_TO_DISPLAY);
        int idx = displayNumbers.indexOf(displayNumber);
        if(idx < 0) {
            return null;
        } else {
            return Path.fromFields("teams", Integer.toString(idx));
        }
    }

    public <T> T getValueForDisplayNumber(int displayNumber, Path<Team, T> subPath) {
        return query(definition, PATH_TO_DISPLAY, i -> i == displayNumber, PATH_TO_ALL_TEAMS.then(subPath.wildcard()));
    }

    public <T, F1, F2> F2 query(CellDefinition<T> definition, Path<T, List<F1>> fieldToQuery, Predicate<F1> predicate, Path<T, List<F2>> fieldToReturn) {
        Tuple2<List<F1>, List<F2>> retrieved =
                retrieveService.bulkRetrieve(definition, fieldToQuery, fieldToReturn);
        for(int idx = 0; idx < retrieved.getT1().size(); idx++) {
            if(predicate.test(retrieved.getT1().get(idx))) {
                return retrieved.getT2().get(idx);
            }
        }
        return null;
    }
}
