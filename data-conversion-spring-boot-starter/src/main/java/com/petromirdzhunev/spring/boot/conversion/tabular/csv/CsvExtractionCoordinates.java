package com.petromirdzhunev.spring.boot.conversion.tabular.csv;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.petromirdzhunev.spring.boot.conversion.tabular.TabularColumn;

public record CsvExtractionCoordinates(
		Set<TabularColumn> groupByColumns,
		Set<TabularColumn> csvColumns
) {
	public CsvExtractionCoordinates merge(final CsvExtractionCoordinates mergeExcelExtractionCoordinates) {
		return new CsvExtractionCoordinates(
				Stream.concat(groupByColumns.stream(), mergeExcelExtractionCoordinates.groupByColumns.stream())
				      .collect(Collectors.toSet()),
				Stream.concat(csvColumns.stream(), mergeExcelExtractionCoordinates.csvColumns.stream())
				      .collect(Collectors.toSet()));
	}
}
