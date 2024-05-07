package com.petromirdzhunev.spring.boot.web.exception;

import java.nio.file.Path;

public class ReadFileException extends RuntimeException {

	public ReadFileException(final Path filePath, final Throwable cause) {
		super("Unable to read file [path=%s]".formatted(filePath.toAbsolutePath().toString()), cause);
	}
}