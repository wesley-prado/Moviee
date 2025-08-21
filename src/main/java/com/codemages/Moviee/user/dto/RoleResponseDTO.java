package com.codemages.Moviee.user.dto;

import org.springframework.hateoas.server.core.Relation;

@Relation(value = "role", collectionRelation = "roles")
public record RoleResponseDTO(String name) {}
