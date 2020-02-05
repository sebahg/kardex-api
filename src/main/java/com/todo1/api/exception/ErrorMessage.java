package com.todo1.api.exception;

import org.springframework.http.HttpStatus;

public class ErrorMessage {
	private HttpStatus errorCode;
	private String errorMessage;
	
	public ErrorMessage() {
		super();
	}
	
	public ErrorMessage(HttpStatus errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public HttpStatus getError() {
		return errorCode;
	}
	
	public void setError(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
