package com.codemages.moviee.controllers.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/home")
	public String homePage() {
		return "home";
	}
}
