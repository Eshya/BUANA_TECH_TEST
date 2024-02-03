package com.eshya.buana_technical_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ServerErrorExceptionHandler {

	@ExceptionHandler(ServerErrorException.class)
	public ResponseEntity<ErrorResponseEntity> handleServerErrorException(ServerErrorException exception, WebRequest request) {
		
		ErrorResponseEntity response = new ErrorResponseEntity();
		response.setError_Code(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setMessage(exception.getMessage());
		response.setTimestamp(new Date());
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
