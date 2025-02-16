package org.j3lsmp.minecraft_stats_backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/**/{path:[^\\.]*}")
	public String redirect() {
		return "forward:/index.html";
	}
}