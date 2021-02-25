package de.fallapalooza.streamapi.controller;

import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.model.Bracket;
import de.fallapalooza.streamapi.model.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bracket")
public class BracketController {
    @Autowired
    private CellDefinition<Bracket> definition;
}
