package com.api.products.config.validation;

public class ErrorFormDto {
	
	private int statusCode;
	private String message;
	
	public ErrorFormDto(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	
}
