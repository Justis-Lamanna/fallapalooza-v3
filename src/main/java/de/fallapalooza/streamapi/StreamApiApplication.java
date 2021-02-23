package de.fallapalooza.streamapi;

import com.fasterxml.jackson.core.type.TypeReference;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.processor.CellDefinitionCompiler;
import de.fallapalooza.streamapi.annotation.retrieve.RetrieveService;
import de.fallapalooza.streamapi.model.Team;
import de.fallapalooza.streamapi.model.Teams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class StreamApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(StreamApiApplication.class, args);
	}

	@Bean
	public Teams teams(RetrieveService retrieveService, CellDefinitionCompiler compiler) {
		CellDefinition<Teams> teamsDefinition = compiler.compile(Teams.class);
		Team team = retrieveService.retrieve(
				teamsDefinition.getDefinitionForField("teams")
						.getDefinitionForField("1", Team.class));
		return null;
	}
}
