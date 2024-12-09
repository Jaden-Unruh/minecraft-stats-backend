package com.example.minecraft_stats_backend;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatsController {
	
	private final Map<String, PlayerStats> allPlayerStats;
	private final Map<String, String> uuidToName;
	
	public StatsController() {
		this.allPlayerStats = StatsParser.allPlayerStats;
		this.uuidToName = MojangAPI.nameCache;
	}
	
	@GetMapping("/stats")
	public Map<String, PlayerStats> getAllPlayerStats() {
		return allPlayerStats;
	}
	
	@GetMapping("/players")
	public Map<String, String> getUuidToName() {
		return uuidToName;
	}
	
	/*
	@GetMapping("/api/stats")
	public String getStats() {
		
		StringBuilder sb = new StringBuilder();
		
		StatsParser.allPlayerStats.forEach((uuid, stats) -> {
			Map<String, Integer> mined = stats.getStats().get("minecraft:mined");
			if (mined != null) {
				sb.append("Player " + MojangAPI.getPlayerName(uuid) + " mined stone: " + mined.getOrDefault("minecraft:stone", 0) + "<br>");
			}
		});
		
		return sb.toString();
	}
	*/
}
