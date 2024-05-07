package com.petromirdzhunev.spring.boot.web.exception;

import java.nio.file.Path;

public class WriteFileException extends RuntimeException {

	public WriteFileException(final Path filePath, final Throwable cause) {
		super("Unable to write file [path=%s]".formatted(filePath.toAbsolutePath().toString()), cause);
	}
}