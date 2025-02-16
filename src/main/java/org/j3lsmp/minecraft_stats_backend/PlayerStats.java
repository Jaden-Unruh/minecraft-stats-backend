package org.j3lsmp.minecraft_stats_backend;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStats {
	
	@JsonProperty("stats")
	private Map<String, Map<String, Integer>> stats;
	
	public Map<String, Map<String, Integer>> getStats() {
		return stats;
	}
	
	public void setStats(Map<String, Map<String, Integer>> stats) {
		this.stats=stats;
	}
} 
