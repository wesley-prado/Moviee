package com.codemages.Moviee.user.dto;

import lombok.Builder;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Relation(value = "role", collectionRelation = "roles")
public record RoleResponseDTO(String name, String description) {}
