package com.codemages.moviee.dtos;

import java.util.UUID;

public record UserResponseDTO(UUID id, String username, String email,
    RoleResponseDTO role, String status) {

}
