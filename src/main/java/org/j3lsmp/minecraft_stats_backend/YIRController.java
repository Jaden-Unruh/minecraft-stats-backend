package org.j3lsmp.minecraft_stats_backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/yir")
public class YIRController {
	
	@GetMapping("/2025/{playerName}")
	public ResponseEntity<Map<String, Object>> get2025(@PathVariable String playerName) {
		Map<String, Object> ret = new HashMap<>();
		
		StatsParser.parseStats();
		Map<String, Map<String, Integer>> playerStats = StatsParser.allPlayerStats
				.get(MojangAPI.uuidCache.get(playerName)).getStats();
		
		if (playerStats == null)
			return ResponseEntity.notFound().build();
		
		ret.put("playTime", playerStats.get("minecraft:custom").get("minecraft:play_time"));
		ret.put("sessions", playerStats.get("minecraft:custom").get("minecraft:leave_game"));
		ret.put("distances", getDistanceStats(playerStats));
		ret.put("mostKilled", getMostStat(playerStats, "minecraft:killed"));
		ret.put("mostDied", getMostStat(playerStats, "minecraft:killed_by"));
		
		return ResponseEntity.ok(ret);
	}
	
	@GetMapping("/2025/{playerName}/maxStats/{stat}")
	public ResponseEntity<List<Map<String, Object>>> get2025maxStats(@PathVariable String playerName, @PathVariable String stat) {
		StatsParser.parseStats();
		Map<String, Map<String, Integer>> playerStats = StatsParser.allPlayerStats.get(MojangAPI.uuidCache.get(playerName)).getStats();
		
		if (playerStats == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(getMostStatArray(playerStats, "minecraft:" + stat));
	}
	
	private static String[] distanceKeys = { "climb", "crouch", "fall", "fly", "sprint", "swim", "walk", "walk_on_water", "walk_under_water", "boat", "aviate", "happy_ghast", "horse", "minecart", "pig", "strider"};
	
	private Map<String, Integer> getDistanceStats(Map<String, Map<String, Integer>> playerStats) {
		Map<String, Integer> distances = new HashMap<>();
		int total = 0;
		for (String key : distanceKeys) {
			try {
				int distance = playerStats.get("minecraft:custom").get("minecraft:" + key + "_one_cm");
				total += distance;
				distances.put(key, distance);
			} catch (NullPointerException e) {}
		}
		distances.put("total", total);
		return distances;
	}
	
	private Map<String, Object> getMostStat(Map<String, Map<String, Integer>> playerStats, String stat) {
		String name = "";
		int value = 0;
		for (Entry<String, Integer> entry : playerStats.get(stat).entrySet())
			if (entry.getValue() > value) {
				value = entry.getValue();
				name = entry.getKey().replace("minecraft:", "");
			}
		Map<String, Object> mostKilled = new HashMap<>();
		mostKilled.put("name", name);
		mostKilled.put("value", value);
		return mostKilled;
	}
	
	private List<Map<String, Object>> getMostStatArray(Map<String, Map<String, Integer>> playerStats, String stat) {
		Map<String, Integer> statMap = playerStats.get(stat);
		List<Entry<String, Integer>> list = new ArrayList<>(statMap.entrySet());
		list.sort((a, b) -> b.getValue().compareTo(a.getValue()));
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (int i = 0; i < Math.min(5, list.size()); i++)
			mapList.add(Map.of("name", list.get(i).getKey().replace("minecraft:", ""), "value", list.get(i).getValue()));
		return mapList;
	}
}
