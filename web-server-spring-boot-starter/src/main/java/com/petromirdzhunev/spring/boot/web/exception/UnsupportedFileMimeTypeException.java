package com.petromirdzhunev.spring.boot.web.exception;

public class UnsupportedFileMimeTypeException extends RuntimeException {

	public UnsupportedFileMimeTypeException(final String mimeType) {
		super("Unsupported file mime type [mimeType=%s]".formatted(mimeType));
	}
}
