package com.codemages.Moviee.auth.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.codemages.Moviee.auth.client.constant.RegexConstant.URL_PATTERN;

public record ClientDTO(
  @NotBlank(message = "This field cannot be blank") @Size(min = 5, max = 100) String clientId,
  @NotBlank(message = "This field cannot be blank") @Size(min = 8, max = 255) String clientSecret,
  @NotBlank(message = "This field cannot be blank") @Size(min = 5, max = 255) String clientName,
  @NotBlank(message = "This field cannot be blank") @Pattern(regexp = URL_PATTERN,
    message = "Invalid URL") String redirectUri) {}
