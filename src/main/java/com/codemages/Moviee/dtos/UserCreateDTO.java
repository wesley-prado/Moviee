package com.codemages.moviee.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
                @NotBlank(message = "should not be blank") @Size(min = 6, max = 24, message = "must be between 6 and 24 characters") String username,
                @NotBlank(message = "should not be blank") @Email(message = "provide a valid email") String email,
                @NotBlank(message = "should not be blank") @Size(min = 6, max = 20, message = "must be between 6 and 20 characters") String password,
                @NotBlank(message = "should not be blank") String role) {}
