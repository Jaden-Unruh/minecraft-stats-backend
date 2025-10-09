package org.j3lsmp.minecraft_stats_backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

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
		Map<String, Map<String, Integer>> playerStats = StatsParser.allPlayerStats
				.get(MojangAPI.uuidCache.get(playerName)).getStats();

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

	@GetMapping("/textures/{itemName}")
	public ResponseEntity<Resource> getItemTexture(@PathVariable String itemName) {

		Resource resource = new ClassPathResource("static/assets/textures/" + itemName + ".png");

		if (!resource.exists())
			resource = new ClassPathResource("static/assets/textures/barrier.png");

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/png").body(resource);
	}

	@GetMapping("/inventories/{playerName}")
	public ResponseEntity<Map<String, List<Map<String, Object>>>> getPlayerInventory(@PathVariable String playerName) {
		try {
			Map<String, List<Map<String, Object>>> inventory = InventoryParser
					.getPlayerInventory(MojangAPI.uuidCache.get(playerName));
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
		StatsParser.parseLeaderboardsTopped();
		try {
			Map<String, String> overview = new HashMap<>();

			overview.put("Advancements",
					Integer.toString(AdvancementParser.getAdvancements(MojangAPI.uuidCache.get(playerName)).size()));
			overview.put("Playtime",
					Integer.toString(StatsParser.allPlayerStats.get(MojangAPI.uuidCache.get(playerName)).getStats()
							.get("minecraft:custom").get("minecraft:play_time")));
			overview.put("Leaderboards", Integer.toString(StatsParser.leaderboardsByPlayer.get(playerName).size()));

//			List<Map<String, Object>> serverData = webClient.get().uri("/players").retrieve().bodyToMono(List.class)
//					.block();
//			Map<String, Object> playerServerData = serverData.stream()
//					.filter(player -> playerName.equalsIgnoreCase((String) player.get("name"))).findFirst()
//					.orElse(Map.of("error", "player not found"));
//
//			if (playerServerData.get("error") == null) {
//				overview.put("Online", "true");
//				overview.put("Health", playerServerData.get("health").toString());
//				overview.put("Position",
//						"(" + playerServerData.get("x").toString() + ", " + playerServerData.get("y").toString() + ", "
//								+ playerServerData.get("z").toString() + ") in the "
//								+ playerServerData.get("dimension").toString());
//			} else {
//				overview.put("Online", "false");
//			}

			return ResponseEntity.ok(overview);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		} catch (IOException | ParseException e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}

	private final File imagesDirectory = MinecraftStatsBackendApplication.IS_LOCAL_TEST
			? new File("G:\\Minecraft Servers\\Azure server\\stats tool testing\\images")
			: new File("E:\\J3L+ Server Live\\webpage\\images");

	@GetMapping("/imageList")
	public ResponseEntity<Set<String>> getImageList() {
		try {
			Set<String> images = Stream.of(imagesDirectory.listFiles()).filter(file -> !file.isDirectory())
					.map(File::getName).collect(Collectors.toSet());

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
			for (String statCategory : playerStats.getStats().keySet()) {
				for (String statKey : playerStats.getStats().get(statCategory).keySet())
					statsNames.add(new Statistic(statCategory, statKey));
			}

		try {
			return ResponseEntity.ok(statsNames);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@GetMapping("/leaderboard/{category}/{key}")
	public ResponseEntity<Map<String, Integer>> getLeaderboard(@PathVariable String category,
			@PathVariable String key) {
		try {
			return ResponseEntity.ok(StatsParser.getLeaderboardByStat(category, key));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@GetMapping("/leaderboardLeaderboard")
	public ResponseEntity<Map<String, Integer>> getLeaderboardLeaderboard() {
		StatsParser.parseLeaderboardsTopped();
		Map<String, Integer> ret = new HashMap<>();
		for (String name : StatsParser.leaderboardsByPlayer.keySet()) {
			ret.put(name, StatsParser.leaderboardsByPlayer.get(name).size());
		}

		try {
			return ResponseEntity.ok(ret);
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
			if (dropped != null)
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

	@GetMapping("/leaderboardsTopped/{playerName}")
	public ResponseEntity<Map<String, Integer>> getLeaderboardsTopped(@PathVariable String playerName) {
		StatsParser.parseLeaderboardsTopped();
		try {
			return ResponseEntity.ok(StatsParser.leaderboardsByPlayer.get(playerName));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}

	private Instant lastUpdate = Instant.MIN;

	@Value("${minecraft.stats.source}")
	private String statsSourcePath;

	@Value("${minecraft.stats.dest}")
	private String statsDestPath;
	
	@Value("${minecraft.advancements.source}")
	private String advancementsSourcePath;
	
	@Value("${minecraft.advancements.dest}")
	private String advancementsDestPath;

	@GetMapping("/lastStatsUpdate")
	public ResponseEntity<Map<String, String>> getLastUpdate() {
		Map<String, String> response = new HashMap<>();
		if (lastUpdate.equals(Instant.MIN))
			response.put("lastUpdate", null);
		else
			response.put("lastUpdate", lastUpdate.toString());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/updateStats")
	public ResponseEntity<Map<String, String>> updateStats() {
		return ResponseEntity.status(HttpStatusCode.valueOf(501)).body(Map.of("Error:", "This feature is temporarily disabled, I want to make sure it's safe and doesn't corrupt any files when used live."));
		/*
		Instant now = Instant.now();
		Duration sinceLast = Duration.between(lastUpdate, now);

		if (sinceLast.compareTo(Duration.ofHours(3)) < 0) {
			return ResponseEntity.status(429)
					.body(Map.of("error", "Update allowed only once every 3 hours", "lastUpdate", lastUpdate.toString()));
		}

		try {
			runScreenCommand("say Updating webpage stats, forcing server save. You will experience rubberbanding in a few seconds...");

			Thread.sleep(1000);

			runScreenCommand("save-all flush");

			Thread.sleep(2000);

			runScreenCommand("save-off");

			Thread.sleep(1000);

			copyFile(statsSourcePath, statsDestPath);
			copyFile(advancementsSourcePath, advancementsDestPath);

			runScreenCommand("save-on");

			now = Instant.now();
			lastUpdate = now;

			runScreenCommand("say Update complete! Sorry for the interruption. Last update " + now.toString());

			return ResponseEntity.ok(Map.of("status", "sucess", "lastUpdate", lastUpdate.toString()));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(Map.of("error", "Update failed: " + e.getMessage()));
		}*/
	}

	private void runScreenCommand(String cmd) throws IOException {
		if (MinecraftStatsBackendApplication.IS_LOCAL_TEST)
			System.out.println("Executed command " + cmd);
		else
			new ProcessBuilder("sudo", "-u", "jaden", "screen", "-S", "mc", "-p", "0", "-X", "stuff", "\r" + cmd + "\r")
					.inheritIO().start();
	}

	private void copyFile(String source, String dest) throws IOException {
		if (MinecraftStatsBackendApplication.IS_LOCAL_TEST)
			System.out.println("Copied file from " + source + " to " + dest);
		else {

			Path sourceDir = Paths.get(source);
			Path destDir = Paths.get(dest);

			if (!Files.exists(sourceDir))
				throw new IOException("Source stats directory cannot be found");
			if (!Files.exists(destDir))
				throw new IOException("Dest stats directory cannot be found");

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir, "*.json")) {
				for (Path file : stream) {
					Path destFile = destDir.resolve(file.getFileName());
					Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
				}
			}
		}
	}
}