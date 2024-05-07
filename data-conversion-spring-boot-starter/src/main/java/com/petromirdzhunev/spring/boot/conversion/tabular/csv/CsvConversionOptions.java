package com.petromirdzhunev.spring.boot.conversion.tabular.csv;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CsvConversionOptions<T> {
    @Builder.Default
    private final MappingStrategy<T> mappingStrategy = new HeaderColumnNameMappingStrategy<>();
    @Builder.Default
    private final Write write = Write.builder().build();
    @Builder.Default
    private final Read read = Read.builder().build();

	/**
	 * Options used when writing to CSV.
	 */
	@Getter
	@Builder
	public static class Write {
		@Builder.Default
		private final char quoteCharacter = ICSVWriter.NO_QUOTE_CHARACTER;
		@Builder.Default
		private final char escapeCharacter = ICSVWriter.NO_ESCAPE_CHARACTER;
		@Builder.Default
		private final char separator = ICSVWriter.DEFAULT_SEPARATOR;
		@Builder.Default
		private final String lineEnd = ICSVWriter.DEFAULT_LINE_END;
	}

	/**
	 * Options that are used when converting CSV to POJOs.
	 */
	@Getter
	@Builder
	public static class Read {
		@Builder.Default
		private final char quoteCharacter = ICSVWriter.NO_QUOTE_CHARACTER;
		@Builder.Default
		private final char escapeCharacter = ICSVWriter.NO_ESCAPE_CHARACTER;
		@Builder.Default
		private final char separator = ICSVWriter.DEFAULT_SEPARATOR;
	}

}
