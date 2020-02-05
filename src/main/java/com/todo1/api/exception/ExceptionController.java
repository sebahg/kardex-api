package com.todo1.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException exception) {
		LOGGER.info("Business Exception: " + exception.getErrorMessage().getErrorMessage());
		return new ResponseEntity<ErrorMessage>(exception.getErrorMessage(), exception.getErrorMessage().getError());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleException(Exception exception) {
		exception.printStackTrace();
		return new ResponseEntity<ErrorMessage>(
				new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "There was an error on the server"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
