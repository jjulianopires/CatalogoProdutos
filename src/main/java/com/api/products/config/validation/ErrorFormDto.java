package com.api.products.config.validation;

public class ErrorFormDto {
	
	private int status_code;
	private String message;
	
	public ErrorFormDto(int status_code, String message) {
		this.status_code = status_code;
		this.message = message;
	}

	public int getStatus_code() {
		return status_code;
	}
	
	public String getMessage() {
		return message;
	}
	
	
	
}
