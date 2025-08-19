package com.codemages.Moviee.security.config;

import com.codemages.Moviee.client.ClientService;
import com.codemages.Moviee.client.dtos.ClientDTO;
import com.codemages.Moviee.user.UserService;
import com.codemages.Moviee.user.dto.UserCreateDTO;
import com.codemages.Moviee.user.dto.UserResponseDTO;
import com.codemages.Moviee.user.enums.DocumentType;
import com.codemages.Moviee.user.enums.Role;
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

      if ( !clientService.existsByClientId( "postman" ) ) {
        clientService.save( new ClientDTO(
          "postman",
          "my_client_secret",
          "Postman Client",
          "https://oauth.pstmn.io/v1/callback"
        ) );
        System.out.println( "Client 'postman' created." );
      }
    };
  }
}