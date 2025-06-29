package com.codemages.Moviee.dtos;

import java.util.UUID;

public record UserResponseDTO(UUID id, String username, String email,
                              String role, String status) {

}
