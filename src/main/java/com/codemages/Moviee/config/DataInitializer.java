package com.codemages.moviee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codemages.moviee.dtos.RoleCreateDTO;
import com.codemages.moviee.dtos.RoleResponseDTO;
import com.codemages.moviee.dtos.UserCreateDTO;
import com.codemages.moviee.services.RoleService;
import com.codemages.moviee.services.UserService;

@Configuration
@Profile("test")
public class DataInitializer {
  private final RoleService roleService;
  private final UserService userService;

  @Autowired
  public DataInitializer(RoleService roleService, UserService userService) {
    this.roleService = roleService;
    this.userService = userService;
  }

  @Bean
  public CommandLineRunner initData() {
    return args -> {
      if (userService.count() == 0) {
        roleService.save(new RoleCreateDTO("ADMIN"));
        roleService.save(new RoleCreateDTO("USER"));

        UserCreateDTO admin = new UserCreateDTO("admin", "admin@mail.com",
            "admin", "ADMIN");
        UserCreateDTO user = new UserCreateDTO("user", "user@mail.com", "user",
            "USER");

        userService.save(admin);
        userService.save(user);
      }
    };
  }
}