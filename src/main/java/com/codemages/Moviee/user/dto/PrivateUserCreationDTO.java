package com.codemages.Moviee.user.dto;

import com.codemages.Moviee.auth.security.password.StrongPassword;
import com.codemages.Moviee.user.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PrivateUserCreationDTO(
  @NotBlank(message = "should not be blank")
  @Size(min = 6,
    max = 24,
    message = "must be between 6 and 24 characters")
  String username,
  @NotBlank(message = "should not be blank")
  @Email(message = "provide a valid email")
  String email,
  @StrongPassword
  String password,
  @NotBlank(message = "should not be blank")
  String document,
  @NotBlank(message = "should not be blank")
  String documentType,
  @NotNull(message = "role must not be null") Role role
) {}
