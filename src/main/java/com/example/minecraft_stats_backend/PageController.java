package com.example.minecraft_stats_backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/stats/{playername}")
	public String stats() {
		return "forward:/index.html";
	}
	
	@GetMapping("/**/{path:[^\\.]*}")
	public String redirect() {
		return "forward:/index.html";
	}
}