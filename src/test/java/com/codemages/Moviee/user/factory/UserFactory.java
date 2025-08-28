package com.codemages.Moviee.user.factory;

import com.codemages.Moviee.auth.security.password.CustomPasswordGenerator;
import com.codemages.Moviee.auth.security.password.PasswordGenerator;
import com.codemages.Moviee.user.constant.DocumentType;
import com.codemages.Moviee.user.constant.Role;
import com.codemages.Moviee.user.constant.UserStatus;
import com.codemages.Moviee.user.dto.PrivateUserCreationDTO;
import com.codemages.Moviee.user.dto.PublicUserCreationDTO;
import com.codemages.Moviee.user.dto.UserResponseDTO;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UserFactory {
  private static final PasswordGenerator passwordGenerator = new CustomPasswordGenerator();

  public static PublicUserCreationDTO createPublicUserDTO() {
    UUID id = java.util.UUID.randomUUID();
    String randomIdentifier = id.toString();
    StringBuilder sb = new StringBuilder( "testuser_" );
    String username = sb.append( Role.USER.getDisplayName().toLowerCase() )
      .append( "_" )
      .append( randomIdentifier )
      .substring( 0, 24 );

    return new PublicUserCreationDTO(
      username,
      username + "@test.com",
      passwordGenerator.generate(),
      "1234567890",
      DocumentType.RG.name()
    );
  }

  public static PrivateUserCreationDTO createPrivateUserDTO(@NotNull Role role) {
    UUID id = java.util.UUID.randomUUID();
    String randomIdentifier = id.toString();
    StringBuilder sb = new StringBuilder( "testuser_" );
    String username = sb.append( role.name().toLowerCase() )
      .append( "_" )
      .append( randomIdentifier )
      .substring( 0, 24 );

    return new PrivateUserCreationDTO(
      username,
      username + "@test.com",
      passwordGenerator.generate(),
      "1234567890",
      DocumentType.RG.name(),
      role
    );
  }

  public static UserResponseDTO createUserResponseDTO(PublicUserCreationDTO dto) {
    return new UserResponseDTO(
      UUID.randomUUID(),
      dto.username(),
      dto.email(),
      Role.USER.getDisplayName(),
      UserStatus.ACTIVE.name()
    );
  }

  public static UserResponseDTO createUserResponseDTO(PrivateUserCreationDTO dto) {
    return new UserResponseDTO(
      UUID.randomUUID(),
      dto.username(),
      dto.email(),
      dto.role().getDisplayName(),
      UserStatus.ACTIVE.name()
    );
  }
}
