package com.petromirdzhunev.spring.boot.conversion.exception;

/**
 * An exception thrown when the data conversion fails.
 */
public class DataConversionException extends RuntimeException {

	public DataConversionException(final String message) {
		super(message);
	}

	public DataConversionException(final String message, final Throwable cause) {
		super(message, cause);
	}
}

