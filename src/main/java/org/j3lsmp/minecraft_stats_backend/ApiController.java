package org.j3lsmp.minecraft_stats_backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
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

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiController {
	
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
		return ResponseEntity.ok(MojangAPI.nameCache);
	}
	
	@GetMapping("/backups")
	public ResponseEntity<JsonNode> getBackups() {
		BackupsParser.parseBackups();
		return ResponseEntity.ok(BackupsParser.getBackups());
	}
	
	private final Path mapDirectory  = Paths.get("/home/azureuser/webpage/map");
	// private final Path mapDirectory = Paths.get("G:\\Minecraft Servers\\Azure server\\stats tool testing\\map");
	
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
	public ResponseEntity<Map<String, Integer>> getOverview(@PathVariable String playerName) {
		try {
			Map<String, Integer> overview = new HashMap<>();
			
			overview.put("Advancements", AdvancementParser.getAdvancements(MojangAPI.uuidCache.get(playerName)).size());
			overview.put("Playtime", StatsParser.allPlayerStats.get(MojangAPI.uuidCache.get(playerName)).getStats().get("minecraft:custom").get("minecraft:play_time"));
			
			return ResponseEntity.ok(overview);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		} catch (IOException | ParseException e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	private final File imagesDirectory  = new File("/home/azureuser/webpage/images");
	// private final File imagesDirectory = new File("G:\\Minecraft Servers\\Azure server\\stats tool testing\\images");
	
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
} 