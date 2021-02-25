package de.fallapalooza.streamapi;

import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.processor.CellDefinitionCompiler;
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
	public CellDefinition<Teams> teams(CellDefinitionCompiler compiler) {
		return compiler.compile(Teams.class);
	}
}
