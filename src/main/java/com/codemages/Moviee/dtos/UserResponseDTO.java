package com.codemages.moviee.dtos;

import java.util.UUID;

public record UserResponseDTO(UUID id, String username, String email,
		String role, String status) {

}
