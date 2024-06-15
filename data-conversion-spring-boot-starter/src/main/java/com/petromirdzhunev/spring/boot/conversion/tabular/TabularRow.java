package com.petromirdzhunev.spring.boot.conversion.tabular;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.util.Assert;

public final class TabularRow implements Iterable<TabularCell> {

	private final List<TabularCell> tabularCells;

	public TabularRow(final Set<TabularColumn> tabularColumns, final Function<Integer,
			String> valueSupplier) {
		Assert.notEmpty(tabularColumns, "tabularColumns must not be empty");
		Assert.notNull(valueSupplier, "valueSupplier must not be null");
		tabularCells = tabularColumns.stream()
		                             .map(tabularColumn -> new TabularCell(tabularColumn.context(),
				                             tabularColumn.valueTransformer()
				                                          .apply(valueSupplier.apply(tabularColumn.index()))))
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