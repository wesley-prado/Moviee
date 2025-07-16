package com.codemages.Moviee.dtos;

import com.codemages.Moviee.utils.password.StrongPassword;
import com.codemages.Moviee.utils.roles.ValidRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
		@NotBlank(message = "should not be blank") @Size(min = 6,
				max = 24,
				message = "must be between 6 and 24 characters") String username,
		@NotBlank(message = "should not be blank") @Email(message = "provide a valid email") String email,
		@StrongPassword String password,
		@NotBlank(message = "should not be blank") String document,
		@NotBlank(message = "should not be blank") String documentType,
		@NotBlank(message = "should not be blank") @ValidRole(message =
				"Invalid user role. Access " +
						"'/api/{api_version}/roles' endpoint to see allowed roles.") String role) {
//  @Override
//  public String toString() {
//    // return JSON representation of the record
//    return String.format(
//      "{\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"document\":\"%s\"," +
//			  "\"documentType\":\"%s\",\"role\":\"%s\"}",
//      username,
//      email,
//      password,
//      document,
//      documentType,
//      role
//    );
//  }
}
