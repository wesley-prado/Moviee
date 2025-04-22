package com.codemages.moviee.dtos;

import com.codemages.moviee.utils.validators.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
		@NotBlank(message = "should not be blank") @Size(min = 6, max = 24, message = "must be between 6 and 24 characters") String username,
		@NotBlank(message = "should not be blank") @Email(message = "provide a valid email") String email,
		@StrongPassword String password,
		@NotBlank(message = "should not be blank") String role,
		@NotBlank(message = "should not be blank") String document,
		@NotBlank(message = "should not be blank") String documentType) {}
