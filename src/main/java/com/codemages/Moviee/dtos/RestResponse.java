package com.codemages.moviee.dtos;

public record RestResponse<T>(
		T data,
		String message,
		Boolean success) {
	public RestResponse(T data, String message) {
		this(data, message, true);
	}

	public RestResponse(T data) {
		this(data, "Operation successful", true);
	}

	public RestResponse(String message) {
		this(null, message, false);
	}
}
