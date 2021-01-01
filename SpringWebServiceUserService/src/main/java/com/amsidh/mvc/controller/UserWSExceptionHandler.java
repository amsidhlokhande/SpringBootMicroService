
package com.amsidh.mvc.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amsidh.mvc.exception.UserException;
import com.amsidh.mvc.model.model.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class UserWSExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public final ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
				new ErrorMessage(new Date(), Optional.ofNullable(ex.getLocalizedMessage()).orElse(ex.toString())),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { NullPointerException.class,
			UserException.class })
	public final ResponseEntity<Object> handleSpecificException(Exception ex, WebRequest request) {

		ErrorMessage errorMessage = new ErrorMessage(new Date(),
				Optional.ofNullable(ex.getLocalizedMessage()).orElse(ex.toString()));

		log.error(errorMessage.toString());

		ResponseEntity.BodyBuilder responseEntity = ex instanceof UserException
				? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				: ResponseEntity.badRequest();

		return responseEntity.headers(new HttpHeaders()).body(errorMessage);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleExceptionInternal(ex, new ErrorMessage(new Date(),
				Optional.ofNullable(ex.getLocalizedMessage()).orElse(ex.toString())), headers, status, request);
	}
}
