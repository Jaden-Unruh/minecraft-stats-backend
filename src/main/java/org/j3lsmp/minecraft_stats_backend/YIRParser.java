package org.j3lsmp.minecraft_stats_backend;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class YIRParser {
	
	static Map<String, PlayerStats> allYirStats = new HashMap<>();
	
	static void parseYIR() {
		String yirFolderPath = MinecraftStatsBackendApplication.IS_LOCAL_TEST ? "G:\\Minecraft Servers\\Azure server\\stats tool testing\\yir" : "E:\\J3L+ Server Live\\webpage\\yir";
		File yirFolder = new File(yirFolderPath);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		if (yirFolder.exists() && yirFolder.isDirectory()) {
			for (File file : yirFolder.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".json")) {
					try {
						PlayerStats yir = objectMapper.readValue(file, PlayerStats.class);
						
						String playerUUID = file.getName().replace(".json", "").replaceAll("-", "");
						allYirStats.put(playerUUID, yir);
					} catch (IOException e) {
						System.err.println("Error reading file: " + file.getName());
						e.printStackTrace();
					}
				}
			}
		} else {
			System.err.println("YIR folder does not exist or is not a directory.");
		}
	}

}
