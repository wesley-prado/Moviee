package com.codemages.moviee.dtos;

import jakarta.validation.constraints.NotBlank;

public record RoleCreateDTO(
                @NotBlank(message = "The name of the role should not be blank") String name) {}
