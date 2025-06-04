package org.j3lsmp.minecraft_stats_backend;

import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class StatsService {
	
	Character[] vowels = {'a', 'e', 'i', 'o', 'u'};
	
	public String getStat(String player, String category, String key) {
		PlayerStats playerStats = StatsParser.allPlayerStats.get(MojangAPI.uuidCache.get(player));
		
		System.out.println("Player: " + player);
		System.out.println("Category: " + category);
		System.out.println("Key: " + key);
		
		System.out.println("UUID: " + MojangAPI.uuidCache.get(player));
		
		if (playerStats == null)
			return "Player not found";
		
		if (playerStats.getStats().containsKey("minecraft:" + category))
			if (playerStats.getStats().get("minecraft:" + category).containsKey("minecraft:" + key))
				if (category.equals("custom"))
					return String.format("%s's %s stat is %d", player, key, playerStats.getStats().get("minecraft:" + category).get("minecraft:" + key));
				else if (category.equals("used"))
					return String.format("%s has %s a%s %s %d times", player, category, (Arrays.stream(vowels).anyMatch(element -> element.equals(key.charAt(0)))) ? "n" : "", key, playerStats.getStats().get("minecraft:" + category).get("minecraft:" + key));
				else
					return String.format("%s has %s %d %s%s", player, ((category.equals("killed_by")) ? "been " : "") + category, playerStats.getStats().get("minecraft:" + category).get("minecraft:" + key), key, (category.equals("killed_by") || category.equals("killed") || category.equals("broken")) ? "s" : "");
			else
				return String.format("Player %s does not have a %s stat for %s", player, category, key);
		else
			return String.format("Player %s does not have any %s stats", player, category);
	}
}
