package de.fallapalooza.streamapi.model;

import de.fallapalooza.streamapi.annotation.Generator;
import de.fallapalooza.streamapi.annotation.Nested;
import lombok.Data;

import java.util.List;

@Data
public class Teams {
    @Nested(type = Team.class, length = 32, generator = @Generator(row = 12, col = 2, rowOffset = 10))
    private List<Team> teams;
}
