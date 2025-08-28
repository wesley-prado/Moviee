package com.codemages.Moviee.user;

import com.codemages.Moviee.user.dto.PrivateUserCreationDTO;
import com.codemages.Moviee.user.dto.PublicUserCreationDTO;
import com.codemages.Moviee.user.enums.DocumentType;
import com.codemages.Moviee.user.enums.Role;

record UserCreationRequest(
  String username,
  String email,
  String password,
  String document,
  DocumentType documentType,
  Role role
) {
  public static UserCreationRequest from(PublicUserCreationDTO dto) {
    return new UserCreationRequest(
      dto.username(),
      dto.email(),
      dto.password(),
      dto.document(),
      DocumentType.fromString( dto.documentType() ),
      Role.USER
    );
  }

  public static UserCreationRequest from(PrivateUserCreationDTO dto) {
    return new UserCreationRequest(
      dto.username(),
      dto.email(),
      dto.password(),
      dto.document(),
      DocumentType.fromString( dto.documentType() ),
      dto.role()
    );
  }
}
