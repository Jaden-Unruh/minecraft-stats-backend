package org.j3lsmp.minecraft_stats_backend;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StatsParser {
	
	static Map<String, PlayerStats> allPlayerStats = new HashMap<>();
	
	static void parseStats() {
		
		String statsFolderPath = MinecraftStatsBackendApplication.IS_LOCAL_TEST ? "G:\\Minecraft Servers\\Azure server\\stats tool testing\\stats" : "/home/jaden/webpage/stats";
		File statsFolder = new File(statsFolderPath);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		if (statsFolder.exists() && statsFolder.isDirectory()) {
			for (File file : statsFolder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".json")) {
					try {
						PlayerStats stats = objectMapper.readValue(file, PlayerStats.class);
						
						String playerUUID = file.getName().replace(".json", "").replaceAll("-", "");
						allPlayerStats.put(playerUUID, stats);
					} catch (IOException e) {
						System.err.println("Error reading file: " + file.getName());
						e.printStackTrace();
					}
				}
			}
		} else {
			System.err.println("Stats folder does not exist or is not a directory.");
		}
	}
	
	static Map<String, Map<String, Integer>> leaderboardsByPlayer = new HashMap<>();
	
	static void parseLeaderboardsTopped() {
		parseStats();
		Map<String, PlayerStats> allStats = allPlayerStats;
		Map<String, LeaderboardStandingHolder> statStandings = new HashMap<>();
		for (String uuid : allStats.keySet()) {
			PlayerStats playerStats = allStats.get(uuid);
			String playerName = MojangAPI.getPlayerName(uuid);
			for (String statCategory : playerStats.getStats().keySet()){
				for (String statKey : playerStats.getStats().get(statCategory).keySet()) {
					int value = playerStats.getStats().get(statCategory).get(statKey);
					String statId = String.format("%s: %s", statCategory.replace("minecraft:", ""), statKey.replace("minecraft:", ""));
					if (statStandings.containsKey(statId)) {
						if (value >= statStandings.get(statId).firstValue) {
							statStandings.get(statId).secondValue = statStandings.get(statId).firstValue;
							statStandings.get(statId).firstValue = value;
							statStandings.get(statId).firstName = playerName;
						} else if (value >= statStandings.get(statId).secondValue)
							statStandings.get(statId).secondValue = value;
					} else {
						statStandings.put(statId, new LeaderboardStandingHolder(playerName, value, 0));
					}
				}
			}
		}
		
		for (String name : MojangAPI.uuidCache.keySet()) {
			Map<String, Integer> stats = new HashMap<>();
			leaderboardsByPlayer.put(name, stats);
		}
		
		for (String stat : statStandings.keySet()) {
			LeaderboardStandingHolder holder = statStandings.get(stat);
			
			if (leaderboardsByPlayer.containsKey(holder.firstName))
				leaderboardsByPlayer.get(holder.firstName).put(stat, holder.firstValue - holder.secondValue);
			else {
				Map<String, Integer> stats = new HashMap<>();
				stats.put(stat, holder.firstValue - holder.secondValue);
				leaderboardsByPlayer.put(holder.firstName, stats);
			}
		}
	}
	
	static Map<String, Integer> getLeaderboardByStat(String category, String key) {
		Map<String, Integer> leaderboard = new HashMap<>();
		for (String uuid : allPlayerStats.keySet()) {
			PlayerStats thisPlayer = allPlayerStats.get(uuid);
			if (thisPlayer.getStats().containsKey("minecraft:" + category))
				if (thisPlayer.getStats().get("minecraft:" + category).containsKey("minecraft:" + key))
					leaderboard.put(MojangAPI.getPlayerName(uuid), thisPlayer.getStats().get("minecraft:" + category).get("minecraft:" + key));
		}
		
		return leaderboard;
	}
}

class LeaderboardStandingHolder {
	String firstName;
	int firstValue, secondValue;
	LeaderboardStandingHolder(String firstName, int firstValue, int secondValue) {
		this.firstName = firstName;
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}
}