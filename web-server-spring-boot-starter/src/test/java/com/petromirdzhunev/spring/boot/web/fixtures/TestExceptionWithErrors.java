package com.petromirdzhunev.spring.boot.web.fixtures;

import java.util.List;

import lombok.Getter;

public class TestExceptionWithErrors extends RuntimeException {

	@Getter
	private final List<String> errors;

	public TestExceptionWithErrors(final String message, final List<String> errors) {
		super(message);
		this.errors = errors;
	}
}