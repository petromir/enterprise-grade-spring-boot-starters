package com.petromirdzhunev.spring.boot.conversion.pdf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import com.github.fge.lambdas.Throwing;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.petromirdzhunev.spring.boot.conversion.exception.DataConversionException;

public class PdfConverter {

	public String pdfToText(final Path path) {
		StringBuilder fullContent = new StringBuilder();
		extractContent(path, fullContent::append);
		return fullContent.toString();
	}

	public List<String> pdfToList(final Path path) {
		List<String> pagesContent = new ArrayList<>();
		extractContent(path, pagesContent::add);
		return pagesContent;
	}

	public String pdfToText(final byte[] pdfContent) {
		StringBuilder fullContent = new StringBuilder();
		extractContent(pdfContent, null, fullContent::append);
		return fullContent.toString();
	}

	public String pdfToText(final byte[] pdfContent, final int pageNumber) {
		StringBuilder fullContent = new StringBuilder();
		extractContent(pdfContent, pageNumber, fullContent::append);
		return fullContent.toString();
	}

	public List<String> pdfToList(final byte[] pdfContent) {
		List<String> pagesContent = new ArrayList<>();
		extractContent(pdfContent, null, pagesContent::add);
		return pagesContent;
	}

	private void extractContent(final Path path, final Consumer<String> contentConsumer) {
		try {
			extractContent(Files.readAllBytes(path), null, contentConsumer);
		} catch (IOException ioe) {
			throw new DataConversionException("Failed to extract content from PDF".formatted(path.toString()),
					ioe);
		}
	}

	public void extractContent(final byte[] pdfContent, final Integer pageNumber, final Consumer<String> contentConsumer) {
		PdfReader reader;
		try {
			reader = new PdfReader(pdfContent);
			IntStream.range(1, reader.getNumberOfPages() + 1)
			         .filter(page -> pageNumber == null || pageNumber == page)
			         .forEach(page -> contentConsumer.accept(
					         Throwing.supplier(() -> PdfTextExtractor.getTextFromPage(reader, page))
					                 .get()));
			reader.close();
		} catch (IOException ioe) {
			throw new DataConversionException("Failed to extract content from PDF".formatted(pageNumber),
					ioe);
		}
	}
}