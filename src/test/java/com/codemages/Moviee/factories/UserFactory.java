package com.codemages.Moviee.factories;

import com.codemages.Moviee.dtos.UserCreateDTO;
import com.codemages.Moviee.dtos.UserResponseDTO;
import com.codemages.Moviee.entities.DocumentType;
import com.codemages.Moviee.entities.Role;
import com.codemages.Moviee.entities.UserStatus;
import com.codemages.Moviee.utils.password.CustomPasswordGenerator;
import com.codemages.Moviee.utils.password.interfaces.PasswordGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UserFactory {
  private final PasswordGenerator passwordGenerator = new CustomPasswordGenerator();

  public UserCreateDTO createValidUserCreateDTO(@NotNull Role role) {
    UUID id = java.util.UUID.randomUUID();
    String randomIdentifier = id.toString();
    StringBuilder sb = new StringBuilder( "testuser_" );
    String username = sb.append( role.name().toLowerCase() )
      .append( "_" )
      .append( randomIdentifier )
      .substring( 0, 24 );
    return new UserCreateDTO(
      username,
      username + "@test.com",
      passwordGenerator.generate(),
      "1234567890",
      DocumentType.RG.name(),
      role.name()
    );
  }

  public UserResponseDTO createValidUserResponseDTO(UserCreateDTO dto) {
    return new UserResponseDTO(
      UUID.randomUUID(),
      dto.username(),
      dto.email(),
      dto.role(),
      UserStatus.ACTIVE.name()
    );
  }
}
