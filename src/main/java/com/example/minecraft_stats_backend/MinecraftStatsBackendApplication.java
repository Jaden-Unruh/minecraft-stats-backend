package com.example.minecraft_stats_backend;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

@SpringBootApplication
public class MinecraftStatsBackendApplication {

	// TO CHANGE TO SERVER-SIDE
		// update file locations in BackupParser.java, StatsParser.java, ApiController.java, InventoryParser.java"
		// ensure prod release in application.properties
	
	public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
		StatsParser.parseStats();
		MojangAPI.getNames();
		BackupsParser.parseBackups();
		AdvancementParser.getAdvancementMappings();
		SpringApplication.run(MinecraftStatsBackendApplication.class, args);
	}

}
