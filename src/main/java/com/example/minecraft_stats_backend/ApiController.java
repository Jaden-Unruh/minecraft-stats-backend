package com.example.minecraft_stats_backend;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@GetMapping("/stats")
	public Map<String, PlayerStats> getAllPlayerStats() {
		StatsParser.parseStats();
		return StatsParser.allPlayerStats;
	}
	
	@GetMapping("/players")
	public Map<String, String> getUuidToName() {
		MojangAPI.getNames();
		return MojangAPI.nameCache;
	}
	
	@GetMapping("/backups")
	public Map<String, String> getBackups() {
		return backups; //TODO fix this after get backup fetch setup
	}
}
