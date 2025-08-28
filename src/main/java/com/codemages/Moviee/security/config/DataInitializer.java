package com.codemages.Moviee.security.config;

import com.codemages.Moviee.client.ClientService;
import com.codemages.Moviee.client.dtos.ClientDTO;
import com.codemages.Moviee.user.UserService;
import com.codemages.Moviee.user.dto.PublicUserCreationDTO;
import com.codemages.Moviee.user.dto.UserResponseDTO;
import com.codemages.Moviee.user.enums.DocumentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile({ "dev", "test" })
@RequiredArgsConstructor
public class DataInitializer {
  private final UserService userService;
  private final ClientService clientService;

  @Bean
  CommandLineRunner initData() {
    return args -> {
      if ( !userService.isUsernameTaken( "myuser" ) ) {
        PublicUserCreationDTO user = new PublicUserCreationDTO(
          "myuser", "user@mail.com",
          "User1#@@", "336189783",
          DocumentType.RG.name()
        );

        UserResponseDTO userResponse = userService.createPublicUser( user );

        log.debug( "Created Public User: {}", userResponse );
      }

      if ( !clientService.existsByClientId( "postman" ) ) {
        clientService.save( new ClientDTO(
          "postman",
          "my_client_secret",
          "Postman Client",
          "https://oauth.pstmn.io/v1/callback"
        ) );

        log.debug( "Created Client: postman" );
      }
    };
  }
}