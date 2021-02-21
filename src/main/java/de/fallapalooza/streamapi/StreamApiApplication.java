package de.fallapalooza.streamapi;

import de.fallapalooza.streamapi.annotation.processor.CellDefinitionCompiler;
import de.fallapalooza.streamapi.annotation.retrieve.RetrieveService;
import de.fallapalooza.streamapi.model.Teams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StreamApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(StreamApiApplication.class, args);
	}

	@Bean
	public Teams teams(RetrieveService retrieveService, CellDefinitionCompiler compiler) {
		Teams teams = retrieveService.retrieve(compiler.compile(Teams.class));
		return teams;
	}
}
