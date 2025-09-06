package org.j3lsmp.minecraft_stats_backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lexiBoards")
public class LexiBoardsApiController {
	@GetMapping("/lb2/perItem/{itemId}")
	public ResponseEntity<Map<String, Integer>> getLeaderboardLeaderboard(@PathVariable String itemId) {
		Map<String, Integer> ret = new HashMap<>();
		addOneToBoard(getHighestInBoard(StatsParser.getLeaderboardByStat("mined", itemId)), ret);
		addOneToBoard(getHighestInBoard(StatsParser.getLeaderboardByStat("broken", itemId)), ret);
		addOneToBoard(getHighestInBoard(StatsParser.getLeaderboardByStat("picked_up", itemId)), ret);
		addOneToBoard(getHighestInBoard(StatsParser.getLeaderboardByStat("crafted", itemId)), ret);
		addOneToBoard(getHighestInBoard(StatsParser.getLeaderboardByStat("dropped", itemId)), ret);
		addOneToBoard(getHighestInBoard(StatsParser.getLeaderboardByStat("used", itemId)), ret);
		
		try {
			return ResponseEntity.ok(ret);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/lb2/perStat/{statId}")
	
	private String getHighestInBoard(Map<String, Integer> board) {
		int highest = 0;
		String highestName = "";
		for (String name : board.keySet())
			if (board.get(name) > highest)
				highestName = name;
		return highestName;
	}
	
	private void addOneToBoard(String name, Map<String, Integer> board) {
		if (name == "")
			return;
		if (board.containsKey(name))
			board.replace(name, board.get(name).intValue() + 1);
		else
			board.put(name, 1);
	}
}