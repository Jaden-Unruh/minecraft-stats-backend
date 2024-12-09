package com.example.minecraft_stats_backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MojangAPI {
	
	static Map<String, String> nameCache = new HashMap<>();
	
	public static void getNames() {
		StatsParser.allPlayerStats.forEach((uuid, stats) -> {
			MojangAPI.getPlayerName(uuid);
		});
	}
	
	public static String getPlayerName(String uuid) {
		
		uuid = uuid.replaceAll("-", "");
		
		if (nameCache.containsKey(uuid))
			return nameCache.get(uuid);
		
		try {
			String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid;
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP error: " + connection.getResponseCode());
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			
			String jsonResponse = response.toString();
			System.out.println(jsonResponse);
			int nameStart = jsonResponse.indexOf("\"name\" : \"") + 10;
			int nameEnd = jsonResponse.indexOf("\"", nameStart);
			
			String name = jsonResponse.substring(nameStart, nameEnd);
			
			nameCache.put(uuid, name);
			
			return name;
		} catch(Exception e) {
			e.printStackTrace();
			return uuid;
		}
	}
	
}
