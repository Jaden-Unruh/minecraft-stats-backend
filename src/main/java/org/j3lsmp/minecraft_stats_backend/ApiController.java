package org.j3lsmp.minecraft_stats_backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	private final WebClient webClient = WebClient.create("http://localhost:3141");
	
	@GetMapping("/allStats")
	public ResponseEntity<Map<String, PlayerStats>> getAllPlayerStats() {
		StatsParser.parseStats();
		return ResponseEntity.ok(StatsParser.allPlayerStats);
	}
	
	@GetMapping("/stats/{playerName}")
	public ResponseEntity<Map<String, Map<String, Integer>>> getplayerStats(@PathVariable String playerName) {
		
		StatsParser.parseStats();
		Map<String, Map<String, Integer>> playerStats = StatsParser.allPlayerStats.get(MojangAPI.uuidCache.get(playerName)).getStats();
		
		if (playerStats == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(playerStats);
	}
	
	@GetMapping("/players")
	public ResponseEntity<Map<String, String>> getUuidToName() {
		MojangAPI.getNames();
		System.out.println(MojangAPI.nameCache.size());
		return ResponseEntity.ok(MojangAPI.nameCache);
	}
	
	@GetMapping("/backups")
	public ResponseEntity<JsonNode> getBackups() {
		BackupsParser.parseBackups();
		return ResponseEntity.ok(BackupsParser.getBackups());
	}

	private final Path mapDirectory = MinecraftStatsBackendApplication.IS_LOCAL_TEST ? Paths.get("G:\\Minecraft Servers\\Azure server\\stats tool testing\\map") : Paths.get("/home/jacc/webpage/map");
	
	@GetMapping("/map/**")
	public ResponseEntity<Resource> getMapFile(HttpServletRequest request) {
		Path filePath = mapDirectory.resolve(request.getRequestURI().replace("/api/map/", "")).normalize();
		
		try {
			Resource resource = new UrlResource(filePath.toUri());
			
			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}
			
			String contentType = Files.probeContentType(filePath);
			
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream").body(resource);
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/textures/{itemName}")
	public ResponseEntity<Resource> getItemTexture(@PathVariable String itemName) {
		
		Resource resource = new ClassPathResource("static/assets/textures/" + itemName + ".png");
		
		if (!resource.exists())
			resource = new ClassPathResource("static/assets/textures/barrier.png");
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/png").body(resource);
	}
	
	@GetMapping("/inventories/{playerName}")
	public ResponseEntity<Map<String, List<Map<String,Object>>>> getPlayerInventory(@PathVariable String playerName) {
		try {
			Map<String, List<Map<String,Object>>> inventory = InventoryParser.getPlayerInventory(MojangAPI.uuidCache.get(playerName));
			return ResponseEntity.ok(inventory);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/advancements/{playerName}")
	public ResponseEntity<Map<String, String>> getAdvancements(@PathVariable String playerName) {
		try {
			Map<String, String> advancements = AdvancementParser.getAdvancements(MojangAPI.uuidCache.get(playerName));
			return ResponseEntity.ok(advancements);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/allAdvancementData")
	public ResponseEntity<Map<String, Map<String, String>>> getAllAdvancementData() {
		return ResponseEntity.ok(AdvancementParser.advancementInfo);
	}
	
	@GetMapping("/playerOverview/{playerName}")
	public ResponseEntity<Map<String, String>> getOverview(@PathVariable String playerName) {
		try {
			Map<String, String> overview = new HashMap<>();
			
			overview.put("Advancements", Integer.toString(AdvancementParser.getAdvancements(MojangAPI.uuidCache.get(playerName)).size()));
			overview.put("Playtime", Integer.toString(StatsParser.allPlayerStats.get(MojangAPI.uuidCache.get(playerName)).getStats().get("minecraft:custom").get("minecraft:play_time")));
			
			/*List<Map<String, Object>> serverData = webClient.get()
					.uri("/players")
					.retrieve()
					.bodyToMono(List.class)
					.block();
			
			Map<String, Object> playerServerData = serverData
					.stream()
					.filter(player -> playerName.equalsIgnoreCase((String) player.get("name")))
					.findFirst()
					.orElse(Map.of("error", "player not found"));
			
			if (playerServerData.get("error") == null) {
				overview.put("Online", "true");
				overview.put("Health", playerServerData.get("health").toString());
				overview.put("Position", "(" + playerServerData.get("x").toString() + ", " + playerServerData.get("y").toString() + ", " + playerServerData.get("z").toString() + ") in the " + playerServerData.get("dimension").toString());
			} else {
				overview.put("Online", "false");
			}*/
				
			return ResponseEntity.ok(overview);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		} catch (IOException | ParseException e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}

	private final File imagesDirectory = MinecraftStatsBackendApplication.IS_LOCAL_TEST ? new File("G:\\Minecraft Servers\\Azure server\\stats tool testing\\images") : new File("/home/jacc/webpage/images");
	
	@GetMapping("/imageList")
	public ResponseEntity<Set<String>> getImageList() {
		try {
			Set<String> images = Stream.of(imagesDirectory.listFiles())
					.filter(file -> !file.isDirectory())
					.map(File::getName)
					.collect(Collectors.toSet());
			
			return ResponseEntity.ok(images);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@GetMapping("/image/{imageFile}")
	public ResponseEntity<Resource> getImage(@PathVariable String imageFile) {
		try {
			Path imagePath = imagesDirectory.toPath().resolve(imageFile);
			Resource resource = new UrlResource(imagePath.toUri());
			
			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (IOException e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/listStats")
	public ResponseEntity<Set<Statistic>> getStatsList() {
		Map<String, PlayerStats> allStats = StatsParser.allPlayerStats;
		Set<Statistic> statsNames = new HashSet<>();
		for (PlayerStats playerStats : allStats.values())
			for (String statCategory : playerStats.getStats().keySet()){
				for (String statKey : playerStats.getStats().get(statCategory).keySet())
					statsNames.add(new Statistic(statCategory, statKey));
			}
		
		try {
			return ResponseEntity.ok(statsNames);
		} catch(Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/leaderboard/{category}/{key}")
	public ResponseEntity<Map<String, Integer>> getLeaderboard(@PathVariable String category, @PathVariable String key) {
		Map<String, PlayerStats> allStats = StatsParser.allPlayerStats;
		Map<String, Integer> leaderboard = new HashMap<>();
		for (String uuid : allStats.keySet()) {
			PlayerStats thisPlayer = allStats.get(uuid);
			if (thisPlayer.getStats().containsKey("minecraft:" + category))
				if (thisPlayer.getStats().get("minecraft:" + category).containsKey("minecraft:" + key))
					leaderboard.put(MojangAPI.getPlayerName(uuid), thisPlayer.getStats().get("minecraft:" + category).get("minecraft:" + key));
		}
		
		try {
			return ResponseEntity.ok(leaderboard);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/allItems")
	public ResponseEntity<Set<String>> getAllItems() {
		HashSet<String> allItems = new HashSet<>();
		
		for (PlayerStats playerStats : StatsParser.allPlayerStats.values()) {
			Map<String, Integer> picked_up = playerStats.getStats().get("minecraft:picked_up");
			if (picked_up != null)
				allItems.addAll(picked_up.keySet());
			
			Map<String, Integer> used = playerStats.getStats().get("minecraft:used");
			if (used != null)
				allItems.addAll(used.keySet());
			
			Map<String, Integer> dropped = playerStats.getStats().get("minecraft:dropped");
			if (used != null)
				allItems.addAll(dropped.keySet());
			
			Map<String, Integer> mined = playerStats.getStats().get("minecraft:mined");
			if (mined != null)
				allItems.addAll(mined.keySet());
			
			Map<String, Integer> broken = playerStats.getStats().get("minecraft:broken");
			if (broken != null)
				allItems.addAll(broken.keySet());
			
			Map<String, Integer> crafted = playerStats.getStats().get("minecraft:crafted");
			if (crafted != null)
				allItems.addAll(crafted.keySet());
		}
		
		try {
			System.out.println(allItems.size());
			return ResponseEntity.ok(allItems);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/itemOverview/{itemId}")
	public ResponseEntity<Map<String, Integer>> getItemOverview(@PathVariable String itemId) {
		Map<String, Integer> ret = new HashMap<>();
		
		int totPicked_up = 0, totUsed = 0, totDropped = 0, totMined = 0, totBroken = 0, totCrafted = 0;
		
		for (PlayerStats playerStats : StatsParser.allPlayerStats.values()) {
			Map<String, Integer> puStats = playerStats.getStats().get("minecraft:picked_up");
			if (puStats != null) {
				Integer picked_up = puStats.get("minecraft:" + itemId);
				if (picked_up != null)
					totPicked_up += picked_up.intValue();
			}
			
			Map<String, Integer> uStats = playerStats.getStats().get("minecraft:used");
			if (uStats != null) {
				Integer used = uStats.get("minecraft:" + itemId);
				if (used != null)
					totUsed += used.intValue();
			}
			
			Map<String, Integer> dStats = playerStats.getStats().get("minecraft:dropped");
			if (dStats != null) {
				Integer dropped = dStats.get("minecraft:" + itemId);
				if (dropped != null)
					totDropped += dropped.intValue();
			}
			
			Map<String, Integer> mStats = playerStats.getStats().get("minecraft:mined");
			if (mStats != null) {
				Integer mined = mStats.get("minecraft:" + itemId);
				if (mined != null)
					totMined += mined.intValue();
			}
			
			Map<String, Integer> bStats = playerStats.getStats().get("minecraft:broken");
			if (bStats != null) {
				Integer broken = bStats.get("minecraft:" + itemId);
				if (broken != null)
					totBroken += broken.intValue();
			}
			
			Map<String, Integer> cStats = playerStats.getStats().get("minecraft:crafted");
			if (cStats != null) {
				Integer crafted = cStats.get("minecraft:" + itemId);
				if (crafted != null)
					totCrafted += crafted.intValue();
			}
		}
		
		ret.put("picked_up", totPicked_up);
		ret.put("used", totUsed);
		ret.put("dropped", totDropped);
		ret.put("mined", totMined);
		ret.put("broken", totBroken);
		ret.put("crafted", totCrafted);
		
		try {
			return ResponseEntity.ok(ret);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/itemOverview")
	public ResponseEntity<Map<String, Integer>> getItemOverview() {
		Map<String, Integer> ret = new HashMap<>();
		
		int totPicked_up = 0, totUsed = 0, totDropped = 0, totMined = 0, totBroken = 0, totCrafted = 0;
		
		for (PlayerStats playerStats : StatsParser.allPlayerStats.values()) {
			Map<String, Integer> puStats = playerStats.getStats().get("minecraft:picked_up");
			if (puStats != null) {
				for (Integer num : puStats.values())
					totPicked_up += num.intValue();
			}
			
			Map<String, Integer> uStats = playerStats.getStats().get("minecraft:used");
			if (uStats != null) {
				for (Integer num : uStats.values())
					totUsed += num.intValue();
			}
			
			Map<String, Integer> dStats = playerStats.getStats().get("minecraft:dropped");
			if (dStats != null) {
				for (Integer num : dStats.values())
					totDropped += num.intValue();
			}
			
			Map<String, Integer> mStats = playerStats.getStats().get("minecraft:mined");
			if (mStats != null) {
				for (Integer num : mStats.values())
					totMined += num.intValue();
			}
			
			Map<String, Integer> bStats = playerStats.getStats().get("minecraft:broken");
			if (bStats != null) {
				for (Integer num : bStats.values())
					totBroken += num.intValue();
			}
			
			Map<String, Integer> cStats = playerStats.getStats().get("minecraft:crafted");
			if (cStats != null) {
				for (Integer num : cStats.values())
					totCrafted += num.intValue();
			}
		}
		
		ret.put("picked_up", totPicked_up);
		ret.put("used", totUsed);
		ret.put("dropped", totDropped);
		ret.put("mined", totMined);
		ret.put("broken", totBroken);
		ret.put("crafted", totCrafted);
		
		try {
			return ResponseEntity.ok(ret);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/allMobs")
	public ResponseEntity<Set<String>> getAllMobs() {
		HashSet<String> allMobs = new HashSet<>();
		
		for (PlayerStats playerStats : StatsParser.allPlayerStats.values()) {
			Map<String, Integer> killed_by = playerStats.getStats().get("minecraft:killed_by");
			if (killed_by != null)
				allMobs.addAll(killed_by.keySet());
			
			Map<String, Integer> killed = playerStats.getStats().get("minecraft:killed");
			if (killed != null)
				allMobs.addAll(killed.keySet());
		}
		
		try {
			System.out.println(allMobs.size());
			return ResponseEntity.ok(allMobs);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/mobOverview/{mobId}")
	public ResponseEntity<Map<String, Integer>> getMobOverview(@PathVariable String mobId) {
		Map<String, Integer> ret = new HashMap<>();
		
		int totKilled_by = 0, totKilled = 0;
		
		for (PlayerStats playerStats : StatsParser.allPlayerStats.values()) {
			Map<String, Integer> kbStats = playerStats.getStats().get("minecraft:killed_by");
			if (kbStats != null) {
				Integer killed_by = kbStats.get("minecraft:" + mobId);
				if (killed_by != null)
					totKilled_by += killed_by.intValue();
			}
			
			Map<String, Integer> kStats = playerStats.getStats().get("minecraft:killed");
			if (kStats != null) {
				Integer killed = kStats.get("minecraft:" + mobId);
				if (killed != null)
					totKilled += killed.intValue();
			}
		}
		
		ret.put("killed_by", totKilled_by);
		ret.put("killed", totKilled);
		
		try {
			return ResponseEntity.ok(ret);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/mobOverview")
	public ResponseEntity<Map<String, Integer>> getMobOverview() {
		Map<String, Integer> ret = new HashMap<>();
		
		int totKilled_by = 0, totKilled = 0;
		
		for (PlayerStats playerStats : StatsParser.allPlayerStats.values()) {
			Map<String, Integer> kbStats = playerStats.getStats().get("minecraft:killed_by");
			if (kbStats != null) {
				for (Integer num : kbStats.values())
					totKilled_by += num.intValue();
			}
			
			Map<String, Integer> kStats = playerStats.getStats().get("minecraft:killed");
			if (kStats != null) {
				for (Integer num : kStats.values())
					totKilled += num.intValue();
			}
		}
		
		ret.put("killed_by", totKilled_by);
		ret.put("killed", totKilled);
		
		try {
			return ResponseEntity.ok(ret);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
} 