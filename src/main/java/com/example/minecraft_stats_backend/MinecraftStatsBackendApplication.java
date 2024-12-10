package com.example.minecraft_stats_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinecraftStatsBackendApplication {

	public static void main(String[] args) {
		StatsParser.parseStats();
		MojangAPI.getNames();
		BackupsParser.parseBackups();
		SpringApplication.run(MinecraftStatsBackendApplication.class, args);
	}

}
