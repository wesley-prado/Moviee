package com.codemages.Moviee.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginModel {
	private String username;
	private String password;
	private Boolean rememberMe = false;
}
