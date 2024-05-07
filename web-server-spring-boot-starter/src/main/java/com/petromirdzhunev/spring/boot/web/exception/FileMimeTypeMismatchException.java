package com.petromirdzhunev.spring.boot.web.exception;

public class FileMimeTypeMismatchException extends RuntimeException {

	public FileMimeTypeMismatchException(final String givenMimeType, final String actualMimeType) {
		super("File mime type mismatch [givenMimeType=%s, actualMimeType=%s]".formatted(givenMimeType, actualMimeType));
	}
}