package org.j3lsmp.minecraft_stats_backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping(value = { "/{path:[^\\.]*}", "/**/{path:^(?!api$)[^\\.]*}" })
    public String forward() {
        return "forward:/index.html";
    }
}