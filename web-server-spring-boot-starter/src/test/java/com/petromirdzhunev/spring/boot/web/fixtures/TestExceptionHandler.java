package com.petromirdzhunev.spring.boot.web.fixtures;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.petromirdzhunev.spring.boot.web.exception.BaseHttpExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class TestExceptionHandler extends BaseHttpExceptionHandler {

	@ExceptionHandler(TestException.class)
	public ProblemDetail handleTestException(final TestException exception) {
		return handleCustomException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(TestExceptionWithErrors.class)
	public ProblemDetail handleTestExceptionWithErrors(final TestExceptionWithErrors exception) {
		return handleCustomException(exception, HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getErrors());
	}
}
