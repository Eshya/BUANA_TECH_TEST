package com.eshya.buana_technical_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseEntity> handleUsernameExistException(ResourceNotFoundException exception, WebRequest request) {
		
		ErrorResponseEntity response = new ErrorResponseEntity();
		response.setError_Code(HttpStatus.NOT_FOUND);
		response.setField(exception.getFieldName());
		response.setValue(exception.getFieldValue() != null ? exception.getFieldValue().toString() : null);
		response.setMessage(exception.getMessage());
		response.setTimestamp(new Date());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
