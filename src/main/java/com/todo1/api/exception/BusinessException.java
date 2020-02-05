package com.todo1.api.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private ErrorMessage errorMessage;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(ErrorMessage errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
