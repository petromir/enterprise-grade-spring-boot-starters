package com.petromirdzhunev.spring.boot.conversion.tabular;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class TabularRow implements Iterable<TabularCell> {

	private final List<TabularCell> tabularCells;

	public TabularRow(final Set<TabularColumn> tabularColumns, Function<Integer,
			String> valueSupplier) {
		tabularCells = tabularColumns.stream()
		                             .map(tabularColumn -> new TabularCell(tabularColumn.context(),
				                      valueSupplier.apply(tabularColumn.index())))
		                             .toList();
	}

	@Override
	public Iterator<TabularCell> iterator() {
		return tabularCells.iterator();
	}

	public Stream<TabularCell> stream() {
		return tabularCells.stream();
	}

	@Override
	public String toString() {
		return "Tabular Row " + tabularCells;
	}
}