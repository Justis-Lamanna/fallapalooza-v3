package de.fallapalooza.streamapi;

import de.fallapalooza.streamapi.annotation.model.Point;
import de.fallapalooza.streamapi.annotation.processor.CellDefinition;
import de.fallapalooza.streamapi.annotation.processor.CellDefinitionCompiler;
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
	public CellDefinition<Teams> def(CellDefinitionCompiler compiler) {
		CellDefinition<Teams> cellDefinition = compiler.compile(Teams.class);
		List<String> team = cellDefinition.resolveCell(Point.ZERO);
		return cellDefinition;
	}
}
