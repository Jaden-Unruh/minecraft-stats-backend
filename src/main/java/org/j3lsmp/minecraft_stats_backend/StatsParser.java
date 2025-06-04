package org.j3lsmp.minecraft_stats_backend;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StatsParser {
	
	static Map<String, PlayerStats> allPlayerStats = new HashMap<>();
	
	static void parseStats() {
		
		String statsFolderPath = MinecraftStatsBackendApplication.IS_LOCAL_TEST ? "G:\\Minecraft Servers\\Azure server\\stats tool testing\\stats" : "/home/jacc/webpage/stats";
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
}