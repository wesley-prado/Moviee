package com.codemages.Moviee.controllers.v1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codemages.Moviee.models.LoginModel;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String login(Model model, @RequestParam(required = false) String error,
			@RequestParam(required = false) String logout) {
		if (error != null) {
			model.addAttribute("error", "Invalid username or password.");
		}
		if (logout != null) {
			model.addAttribute("message", "You have been logged out successfully.");
		}

		model.addAttribute("loginModel", new LoginModel());

		return "login";
	}
}
