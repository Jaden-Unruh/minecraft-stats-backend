package com.example.minecraft_stats_backend;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BackupsParser {
	
	static Map<String, String> allBackups = new HashMap<>();
	
	static void parseBackups() {
		String backupsName = "G:\\Minecraft Servers\\Azure server\\stats tool testing\\backups.txt";
		File backupsFile = new File(backupsName);
		
		//TODO: get lines and split into timestamp/link, add to map
	}
}
