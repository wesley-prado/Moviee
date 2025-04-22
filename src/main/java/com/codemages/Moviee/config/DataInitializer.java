package com.codemages.moviee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codemages.moviee.dtos.ClientDTO;
import com.codemages.moviee.dtos.RoleCreateDTO;
import com.codemages.moviee.dtos.UserCreateDTO;
import com.codemages.moviee.entities.DocumentType;
import com.codemages.moviee.services.ClientService;
import com.codemages.moviee.services.RoleService;
import com.codemages.moviee.services.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer {
	@Autowired
	private final RoleService roleService;
	@Autowired
	private final UserService userService;
	@Autowired
	private final ClientService clientService;

	@Bean
	CommandLineRunner initData() {
		return args -> {
			if (userService.count() == 0) {
				roleService.save(new RoleCreateDTO("ADMIN"));
				roleService.save(new RoleCreateDTO("USER"));

				UserCreateDTO admin = new UserCreateDTO("admin", "admin@mail.com",
						"Admin1#@", "ADMIN", "30342640038",
						DocumentType.CPF.name());
				UserCreateDTO user = new UserCreateDTO("user", "user@mail.com",
						"User1#@@", "USER", "336189783",
						DocumentType.RG.name());

				userService.save(admin);
				userService.save(user);
			}

			if (clientService.count() == 0) {
				var dto = new ClientDTO("my_client_id",
						"my_client_secret",
						"http://127.0.0.1:8080/login/oauth2/code/client-server");

				clientService.save(dto);
			}
		};
	}
}