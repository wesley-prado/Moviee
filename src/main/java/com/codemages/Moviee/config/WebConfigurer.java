package com.codemages.moviee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.validation.constraints.NotNull;

@Configuration
@EnableWebMvc
public class WebConfigurer implements WebMvcConfigurer {

	@Override
	public void addViewControllers(@NotNull ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
}
