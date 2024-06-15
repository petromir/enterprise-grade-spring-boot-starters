package com.petromirdzhunev.spring.boot.conversion.tabular.excel;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.petromirdzhunev.spring.boot.conversion.tabular.TabularColumn;

public record ExcelExtractionCoordinates(
		String sheetName,
		Set<TabularColumn> groupByColumns,
		Set<TabularColumn> excelColumns,
		long startRow,
		long endRow
) {
	public ExcelExtractionCoordinates merge(final ExcelExtractionCoordinates mergeExcelExtractionCoordinates) {
		validateMergeCoordinates(mergeExcelExtractionCoordinates);
		return new ExcelExtractionCoordinates(
				sheetName,
				Stream.concat(groupByColumns.stream(), mergeExcelExtractionCoordinates.groupByColumns.stream())
				      .collect(Collectors.toSet()),
				Stream.concat(excelColumns.stream(), mergeExcelExtractionCoordinates.excelColumns.stream())
				      .collect(Collectors.toSet()),
				mergeExcelExtractionCoordinates.startRow,
				mergeExcelExtractionCoordinates.endRow
		);
	}

	private void validateMergeCoordinates(final ExcelExtractionCoordinates mergeExcelExtractionCoordinates) {
		if (!sheetName.equals(mergeExcelExtractionCoordinates.sheetName)) {
			throw new IllegalArgumentException("Sheet name differs when merging [sheetName=%s, mergeSheetName=%s]"
					.formatted(sheetName, mergeExcelExtractionCoordinates.sheetName));
		}
		if (startRow != mergeExcelExtractionCoordinates.startRow || endRow != mergeExcelExtractionCoordinates.endRow) {
			throw new IllegalArgumentException(
					"Start or end rows differ when merging [startRow=%d, mergeStartRow=%d, endRow=%d, mergeEndRow=%d]"
							.formatted(startRow, mergeExcelExtractionCoordinates.startRow, endRow,
									mergeExcelExtractionCoordinates.endRow));
		}
	}
}
