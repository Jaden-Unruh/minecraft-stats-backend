package org.j3lsmp.minecraft_stats_backend;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@Configuration
@EnableAutoConfiguration(exclude=ErrorMvcAutoConfiguration.class)
@SpringBootApplication
public class MinecraftStatsBackendApplication {
	
	static final boolean IS_LOCAL_TEST = false;
	
	public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
		StatsParser.parseStats();
		MojangAPI.getNames();
		BackupsParser.parseBackups();
		AdvancementParser.getAdvancementMappings();
		SpringApplication.run(MinecraftStatsBackendApplication.class, args);
	}
}