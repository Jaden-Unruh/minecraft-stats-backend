package org.j3lsmp.minecraft_stats_backend;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/map")
public class MapApi {
	
	Map<String, String> dimensionDirectories = Map.of(
			"overworld", MinecraftStatsBackendApplication.IS_LOCAL_TEST
				? "G:\\Minecraft Servers\\Azure server\\stats tool testing\\map\\overworld"
				: "/home/jaden/webpage/map/overworld",
			"nether", MinecraftStatsBackendApplication.IS_LOCAL_TEST
				? "G:\\Minecraft Servers\\Azure server\\stats tool testing\\map\\nether"
				: "/home/jaden/webpage/map/nether",
			"end", MinecraftStatsBackendApplication.IS_LOCAL_TEST
				? "G:\\Minecraft Servers\\Azure server\\stats tool testing\\map\\end"
				: "/home/jaden/webpage/map/end",
			"netherroof", MinecraftStatsBackendApplication.IS_LOCAL_TEST
				? "G:\\Minecraft Servers\\Azure server\\stats tool testing\\map\\netherroof"
				: "/home/jaden/webpage/map/netherroof");
	
	
	@GetMapping("/{dimension}/**")
	public ResponseEntity<Resource> getMapFile(@PathVariable String dimension, HttpServletRequest request) {
		Path filePath = Paths.get(dimensionDirectories.get(dimension)).resolve(request.getRequestURI().replace("/api/map/"+dimension+"/", "").replaceAll("/", MinecraftStatsBackendApplication.IS_LOCAL_TEST ? "\\\\": "/")).normalize();

		try {
			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}

			String contentType = Files.probeContentType(filePath);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream")
					.body(resource);
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/{dimension}/index.html")
	public ResponseEntity<String> getMapIndexFile(@PathVariable String dimension, @RequestHeader(value = "Referer", required = false) String referer, HttpServletRequest request) {
		
		String paramValue = "false";
		if (referer != null) {
			try {
				URI refererUri = URI.create(referer);
				String query = refererUri.getQuery();
				if (query != null)
					for (String part : query.split("&")) {
						String[] kv = part.split("=", 2);
						if (kv.length == 2 && kv[0].equals("labels"))
							paramValue = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
					}
			} catch(Exception e) {}
		}
		
		Path filePath = Paths.get(dimensionDirectories.get(dimension)).resolve(request.getRequestURI().replace("/api/map/"+dimension+"/", "").replaceAll("/", MinecraftStatsBackendApplication.IS_LOCAL_TEST ? "\\\\": "/")).normalize();

		try {

			if (!Files.exists(filePath)) {
				return ResponseEntity.notFound().build();
			}
			
			String html = Files.readString(filePath);
			
			if (!paramValue.equals("true"))
				html = html.replaceAll("src=\"custom.markers.js\"", "src=\"nocustom.markers.js\"");
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "text/html")
					.body(html);
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
