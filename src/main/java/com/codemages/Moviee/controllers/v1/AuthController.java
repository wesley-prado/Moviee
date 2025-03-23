package com.codemages.moviee.controllers.v1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.codemages.moviee.models.LoginModel;

@Controller
public class AuthController {
	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("loginModel", new LoginModel());

		return "login";
	}
}
