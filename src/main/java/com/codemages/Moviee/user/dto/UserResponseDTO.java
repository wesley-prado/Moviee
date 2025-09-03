package com.codemages.Moviee.user.dto;

import lombok.Builder;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Builder
@Relation(value = "user", collectionRelation = "users")
public record UserResponseDTO(UUID id, String username, String email,
                              String role, String status) {

}
