package org.j3lsmp.minecraft_stats_backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/yir")
public class YIRController {
	
	static Map<String, String> playerLedStats = new HashMap<>();
	
	static void calcPlayerLedStats() {
		Set<LedStat> ledStats = new HashSet<>();
		Set<String> checkedStats = new HashSet<>();
		Map<String, PlayerStats> yirStats = YIRParser.allYirStats;
		for (String uuid : yirStats.keySet()) {
			for (String category : yirStats.get(uuid).getStats().keySet()) {
				if (category.equals("minecraft:custom"))
					continue;
				for (String key : yirStats.get(uuid).getStats().get(category).keySet()) {
					if (checkedStats.add(key + "/" + category)) {
						ArrayList<LeaderboardEntry> leaderboard = getLeaderboardByStat(category, key);
						int total = 0;
						for (LeaderboardEntry entry : leaderboard)
							total += entry.value;
						if (total >= 100) {
							String leader = leaderboard.get(0).uuid;
							int diff = leaderboard.get(0).value - (leaderboard.size() > 1 ? leaderboard.get(1).value : 0);
							ledStats.add(new LedStat(key + "/" + category, leader, diff));
						}
					}
				}
			}
		}
		
		for (String uuid : yirStats.keySet()) {
			int currentMax = 0;
			for (LedStat ledStat : ledStats) {
				if (ledStat.playerLeading().equals(uuid) && ledStat.diff() > currentMax) {
					currentMax = ledStat.diff();
					playerLedStats.put(uuid, ledStat.id());
				}
			}
		}
	}
	
	private static ArrayList<LeaderboardEntry> getLeaderboardByStat(String category, String key) {
		ArrayList<LeaderboardEntry> leaderboard = new ArrayList<>();
		for (String uuid : YIRParser.allYirStats.keySet()) {
			PlayerStats thisPlayer = YIRParser.allYirStats.get(uuid);
			if (thisPlayer.getStats().containsKey(category))
				if (thisPlayer.getStats().get(category).containsKey(key))
					leaderboard.add(new LeaderboardEntry(uuid, thisPlayer.getStats().get(category).get(key)));
		}
		
		Collections.sort(leaderboard);
		
		return leaderboard;
	}
	
	private static class LeaderboardEntry implements Comparable<LeaderboardEntry> {
		String uuid;
		int value;
		
		LeaderboardEntry(String uuid, int value) {
			this.uuid = uuid;
			this.value = value;
		}
		
		@Override
		public int compareTo(LeaderboardEntry o) {
			return o.value - value;
		}
	}
	
	private record LedStat (String id, String playerLeading, int diff) {
	}
	
	@GetMapping("/2025/{playerName}/ledStat")
	public ResponseEntity<String> getLed2025(@PathVariable String playerName) {
		
		String uuid = MojangAPI.getUUID(playerName);
		
		if (playerLedStats.containsKey(uuid))
			return ResponseEntity.ok(playerLedStats.get(uuid).replaceAll("minecraft:", ""));
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/2025/{playerName}")
	public ResponseEntity<Map<String, Object>> get2025(@PathVariable String playerName) {
		Map<String, Object> ret = new HashMap<>();
		Map<String, Map<String, Integer>> yirStats = YIRParser.allYirStats
				.get(MojangAPI.uuidCache.get(playerName)).getStats();
		
		if (yirStats == null)
			return ResponseEntity.notFound().build();
		
		ret.put("playTime", yirStats.get("minecraft:custom").get("minecraft:play_time"));
		ret.put("sessions", yirStats.get("minecraft:custom").get("minecraft:leave_game"));
		ret.put("distances", getDistanceStats(yirStats));
		ret.put("mostKilled", getMostStat(yirStats, "minecraft:killed"));
		ret.put("mostDied", getMostStat(yirStats, "minecraft:killed_by"));
		
		return ResponseEntity.ok(ret);
	}
	
	@GetMapping("/2025/{playerName}/maxStats/{stat}")
	public ResponseEntity<List<Map<String, Object>>> get2025maxStats(@PathVariable String playerName, @PathVariable String stat) {
		Map<String, Map<String, Integer>> yirStats = YIRParser.allYirStats.get(MojangAPI.uuidCache.get(playerName)).getStats();
		
		if (yirStats == null)
			return ResponseEntity.notFound().build();
		
		return ResponseEntity.ok(getMostStatArray(yirStats, "minecraft:" + stat));
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
