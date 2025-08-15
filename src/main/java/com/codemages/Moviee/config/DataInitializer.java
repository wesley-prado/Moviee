package com.codemages.Moviee.config;

import com.codemages.Moviee.dtos.ClientDTO;
import com.codemages.Moviee.dtos.UserCreateDTO;
import com.codemages.Moviee.dtos.UserResponseDTO;
import com.codemages.Moviee.entities.DocumentType;
import com.codemages.Moviee.entities.Role;
import com.codemages.Moviee.services.ClientService;
import com.codemages.Moviee.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({ "dev", "test" })
@RequiredArgsConstructor
public class DataInitializer {
  private final UserService userService;
  private final ClientService clientService;

  @Bean
  CommandLineRunner initData() {
    return args -> {
      if ( !userService.isUsernameTaken( "user" ) ) {
        UserCreateDTO user = new UserCreateDTO(
          "user", "user@mail.com",
          "User1#@@", "336189783",
          DocumentType.RG.name(), Role.USER.name()
        );

        UserResponseDTO userResponse = userService.createUser( user );

        System.out.println( "User created: " + userResponse.id() );
      }

      if ( clientService.count() == 0 ) {
        clientService.save( new ClientDTO(
          "postman",
          "my_client_secret",
          "Postman Client",
          "https://oauth.pstmn.io/v1/callback"
        ) );
      }
    };
  }
}