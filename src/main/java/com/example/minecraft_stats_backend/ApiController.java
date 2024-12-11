package com.example.minecraft_stats_backend;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@GetMapping("/allStats")
	public Map<String, PlayerStats> getAllPlayerStats() {
		StatsParser.parseStats();
		return StatsParser.allPlayerStats;
	}
	
	@GetMapping("/stats/{playerName}")
	public Map<String, Map<String, Integer>> getplayerStats(@PathVariable String playerName) {
		
		StatsParser.parseStats();
		Map<String, Map<String, Integer>> playerStats = StatsParser.allPlayerStats.get(MojangAPI.uuidCache.get(playerName)).getStats();
		
		if (playerStats == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
		}
		
		return playerStats;
	}
	
	@GetMapping("/players")
	public Map<String, String> getUuidToName() {
		MojangAPI.getNames();
		return MojangAPI.nameCache;
	}
	
	@GetMapping("/backups")
	public JsonNode getBackups() {
		return BackupsParser.getBackups();
	}
}