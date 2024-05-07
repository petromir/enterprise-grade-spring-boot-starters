package com.petromirdzhunev.spring.boot.conversion.tabular;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TabularGroupingColumn {
	private final Map<Object, String> valuePerContext;

	public TabularGroupingColumn(final Set<TabularColumn> tabularColumns, Function<Integer,
			String> valueSupplier) {
		this.valuePerContext = tabularColumns
				.stream()
				// Skip rows which grouping column values are empty, as such rows are not needed for calculation
				.filter(tabularColumn -> !valueSupplier.apply(tabularColumn.index()).isEmpty())
				.collect(Collectors.toMap(TabularColumn::context,
						tabularColumn -> valueSupplier.apply(tabularColumn.index())));
	}

	public String contextValue(final Object context) {
		return valuePerContext.get(context);
	}

	public boolean hasValues() {
		return !valuePerContext.isEmpty();
	}
}