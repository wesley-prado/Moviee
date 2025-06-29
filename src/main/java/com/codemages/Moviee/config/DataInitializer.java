package com.codemages.Moviee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.codemages.Moviee.dtos.ClientDTO;
import com.codemages.Moviee.dtos.UserCreateDTO;
import com.codemages.Moviee.dtos.UserResponseDTO;
import com.codemages.Moviee.entities.DocumentType;
import com.codemages.Moviee.entities.Role;
import com.codemages.Moviee.services.ClientService;
import com.codemages.Moviee.services.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile({ "dev", "test" })
@RequiredArgsConstructor
public class DataInitializer {
  @Autowired
  private final UserService userService;
  @Autowired
  private final ClientService clientService;

  @Bean
  CommandLineRunner initData() {
    return args -> {
      if ( userService.count() == 0 ) {
        UserCreateDTO admin = new UserCreateDTO(
          "admin", "admin@mail.com",
          "Admin1#@", "30342640038",
          DocumentType.CPF.name(), Role.ADMIN.name()
        );
        UserCreateDTO user = new UserCreateDTO(
          "user", "user@mail.com",
          "User1#@@", "336189783",
          DocumentType.RG.name(), Role.USER.name()
        );

        UserResponseDTO adminResponse = userService.createUser( admin );
        UserResponseDTO userResponse = userService.createUser( user );

        if ( adminResponse == null || userResponse == null ) {
          throw new RuntimeException( "Failed to create initial users" );
        }

        System.out.println( "Admin created: " + adminResponse.id() );
        System.out.println( "User created: " + userResponse.id() );
      }

      if ( clientService.count() == 0 ) {
        var dto = new ClientDTO(
          "my_client_id",
          "my_client_secret",
          "http://127.0.0.1:8080/login/oauth2/code/client-server"
        );

        clientService.save( dto );
      }
    };
  }
}