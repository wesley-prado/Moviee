package com.codemages.Moviee.user;

import com.codemages.Moviee.user.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

  User toEntity(UserCreationRequest request);

  UserResponseDTO toResponseDTO(User user);
}
