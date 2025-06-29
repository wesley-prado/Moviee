package com.codemages.Moviee.exceptions.user;

public class UserNotCreatedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotCreatedException(String message) {
		super(message);
	}

	public UserNotCreatedException(String message, Throwable cause) {
		super(message, cause);
	}
}
