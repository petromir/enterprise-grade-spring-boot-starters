package com.petromirdzhunev.spring.boot.conversion.tabular.csv;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.petromirdzhunev.spring.boot.conversion.exception.DataConversionException;
import com.petromirdzhunev.spring.boot.conversion.tabular.TabularGroupingColumn;
import com.petromirdzhunev.spring.boot.conversion.tabular.TabularRow;

public class CsvConverter {

	public <T> List<T> csvToList(Class<T> clazz, String csv) {
		return csvToList(clazz, CsvConversionOptions.<T>builder().build(), csv);
	}

	public <T> Map<TabularGroupingColumn, List<TabularRow>> csvToMap(CsvConversionOptions<T> converterOptions,
			String csv, CsvExtractionCoordinates csvExtractionCoordinates) {
		return csvToList(converterOptions, csv)
				.stream()
				.collect(Collectors.toMap(csvCells -> tabularGroupingColumn(csvCells, csvExtractionCoordinates),
						csvCells -> tabularRows(csvCells, csvExtractionCoordinates)));
	}

	public Map<TabularGroupingColumn, List<TabularRow>> csvToMap(String csv,
			CsvExtractionCoordinates csvExtractionCoordinates) {
		return csvToList(CsvConversionOptions.builder().build(), csv)
				.stream()
				.collect(Collectors.toMap(csvCells -> tabularGroupingColumn(csvCells, csvExtractionCoordinates),
						csvCells -> tabularRows(csvCells, csvExtractionCoordinates)));
	}

	public List<String[]> csvToList(String csv) {
		return csvToList(CsvConversionOptions.builder().build(), csv);
	}

	public <T> List<String[]> csvToList(CsvConversionOptions<T> converterOptions, String csv) {
		try (CSVReader csvReader = delegateCsvReader(converterOptions.getRead(), csv)) {
			List<String[]> csvRows = csvReader.readAll();
			if (!csvRows.isEmpty()) {
				csvRows = csvRows.subList(1, csvRows.size());
			}
			return csvRows;
		} catch (CsvException | IOException e) {
			throw new DataConversionException("Failed to convert Csv to List", e);
		}
	}

	public <T> List<T> csvToList(Class<T> clazz, CsvConversionOptions<T> converterOptions, String csv) {
		try (CSVReader csvReader = delegateCsvReader(converterOptions.getRead(), csv)) {
			MappingStrategy<T> mappingStrategy = converterOptions.getMappingStrategy();
			mappingStrategy.setType(clazz);

			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
					.withMappingStrategy(mappingStrategy)
					.build();

			return csvToBean.parse();
		} catch (IOException e) {
			throw new DataConversionException(
					"Failed to convert Csv to List [listType=%s]".formatted(clazz.getSimpleName()), e);
		}
	}

	private CSVReader delegateCsvReader(CsvConversionOptions.Read csvReaderOptions, String csv) {
		return new CSVReaderBuilder(new StringReader(csv))
				.withCSVParser(new CSVParserBuilder()
						.withSeparator(csvReaderOptions.getSeparator())
						.withEscapeChar(csvReaderOptions.getEscapeCharacter())
						.withQuoteChar(csvReaderOptions.getQuoteCharacter())
						.build())
				.build();
	}

	public <T> void collectionToCsv(Collection<T> collection, Class<T> clazz, OutputStream outputStream) {
		streamToCsv(collection.stream(), clazz, outputStream);
	}

	public <T> void collectionToCsv(Collection<T> collection, Class<T> clazz, CsvConversionOptions<T> converterOptions,
			OutputStream outputStream) {
		streamToCsv(collection.stream(), clazz, converterOptions, outputStream);
	}

	public <T> void streamToCsv(Stream<T> stream, Class<T> clazz, OutputStream outputStream) {
		streamToCsv(stream, clazz, CsvConversionOptions.<T>builder().build(), outputStream);
	}

	public <T> void streamToCsv(Stream<T> stream, Class<T> clazz, CsvConversionOptions<T> converterOptions,
			OutputStream outputStream) {
		try (OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
		     ICSVWriter icsvWriter = delegateCsvWriter(streamWriter, converterOptions.getWrite())) {

			streamToCsv(stream, clazz, converterOptions, icsvWriter);
		} catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
			throw new RuntimeException("Failed to convert Type to CSV [type=%s]".formatted(clazz.getSimpleName()), e);
		}
	}

	// todo: create a mapping strategy that could include or not include the header while appending to the file,
	//  as well as account for case-sensitive column names
	public <T> void streamToCsv(Stream<T> stream, Class<T> clazz, CsvConversionOptions<T> converterOptions,
			Path filePath) {
		streamToCsv(stream, clazz, converterOptions, filePath, false);
	}

	public <T> void streamToCsv(Stream<T> stream, Class<T> clazz, CsvConversionOptions<T> converterOptions,
			Path filePath, boolean shouldAppend) {
		try (OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(filePath.toFile(), shouldAppend));
		     ICSVWriter icsvWriter = delegateCsvWriter(streamWriter, converterOptions.getWrite())) {

			streamToCsv(stream, clazz, converterOptions, icsvWriter);
		} catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
			throw new RuntimeException("Failed to convert Type to CSV [type=%s]".formatted(clazz.getSimpleName()), e);
		}
	}

	private <T> void streamToCsv(Stream<T> stream, Class<T> clazz, CsvConversionOptions<T> converterOptions,
			ICSVWriter icsvWriter)
			throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

		MappingStrategy<T> mappingStrategy = converterOptions.getMappingStrategy();
		mappingStrategy.setType(clazz);

		StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(icsvWriter)
				.withMappingStrategy(mappingStrategy)
				.build();

		beanToCsv.write(stream);
	}

	private ICSVWriter delegateCsvWriter(OutputStreamWriter streamWriter,
			CsvConversionOptions.Write writeOptions) {
		return new CSVWriterBuilder(streamWriter)
				.withQuoteChar(writeOptions.getQuoteCharacter())
				.withEscapeChar(writeOptions.getEscapeCharacter())
				.withLineEnd(writeOptions.getLineEnd())
				.withSeparator(writeOptions.getSeparator())
				.build();
	}

	private List<TabularRow> tabularRows(final String[] csvCells,
			final CsvExtractionCoordinates extractionCoordinates) {
		return List.of(new TabularRow(extractionCoordinates.csvColumns(), index -> csvCells[index]));
	}

	private TabularGroupingColumn tabularGroupingColumn(final String[] csvCells,
			final CsvExtractionCoordinates extractionCoordinates) {
		return new TabularGroupingColumn(extractionCoordinates.groupByColumns(), index -> csvCells[index]);
	}
}
