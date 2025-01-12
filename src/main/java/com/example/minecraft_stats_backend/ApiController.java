package com.example.minecraft_stats_backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@GetMapping("/allStats")
	public Map<String, PlayerStats> getAllPlayerStats() {
		StatsParser.parseStats();
		return StatsParser.allPlayerStats;
	}
	
	@GetMapping("/stats/{playerName}")
	public Map<String, Map<String, Integer>> getplayerStats(@PathVariable String playerName) {
		
		StatsParser.parseStats();
		Map<String, Map<String, Integer>> playerStats = StatsParser.allPlayerStats.get(MojangAPI.uuidCache.get(playerName)).getStats();
		
		if (playerStats == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
		}
		
		return playerStats;
	}
	
	@GetMapping("/players")
	public Map<String, String> getUuidToName() {
		MojangAPI.getNames();
		return MojangAPI.nameCache;
	}
	
	@GetMapping("/backups")
	public JsonNode getBackups() {
		BackupsParser.parseBackups();
		return BackupsParser.getBackups();
	}
	
	//private final Path mapDirectory  = Paths.get("/home/azureuser/webpage/map");
	private final Path mapDirectory = Paths.get("G:\\Minecraft Servers\\Azure server\\stats tool testing\\map");
	
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
} 