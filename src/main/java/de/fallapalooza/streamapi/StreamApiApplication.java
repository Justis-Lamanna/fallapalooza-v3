package de.fallapalooza.streamapi;

import com.fasterxml.jackson.core.type.TypeReference;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.processor.CellDefinitionCompiler;
import de.fallapalooza.streamapi.annotation.retrieve.Path;
import de.fallapalooza.streamapi.annotation.retrieve.RetrieveService;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.model.Teams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class StreamApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(StreamApiApplication.class, args);
	}

	@Bean
	public Teams teams(RetrieveService retrieveService, CellDefinitionCompiler compiler) {
		CellDefinition<Teams> teamsDefinition = compiler.compile(Teams.class);

		List<Path<Teams, Team>> definitions = Arrays.asList(
				Path.fromFields("teams", "1"),
				Path.fromFields("teams", "3"),
				Path.fromFields("teams", "6"),
				Path.fromFields("teams", "9")
		);
		List<Team> teams = retrieveService.bulkRetrieve(teamsDefinition, definitions);
		return null;
	}
}
