package com.petromirdzhunev.spring.boot.conversion.tabular;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class TabularGroupingColumn {
	private final Map<Object, String> valuePerContext;

	public TabularGroupingColumn(final Set<TabularColumn> tabularColumns, final Function<Integer,
			String> valueSupplier) {
		Assert.notEmpty(tabularColumns, "tabularColumns must not be empty");
		Assert.notNull(valueSupplier, "valueSupplier must not be null");
		this.valuePerContext = tabularColumns
				.stream()
				// By default, skip rows which grouping column values are empty, as such rows are not needed for
				// calculation
				.filter(tabularColumn -> !valueSupplier.apply(tabularColumn.index()).isEmpty())
				.collect(Collectors.toMap(TabularColumn::context,
						tabularColumn -> tabularColumn.valueTransformer().apply(valueSupplier.apply(tabularColumn.index()))));
	}

	public String contextValue(final Object context) {
		return valuePerContext.get(context);
	}

	public boolean hasValues() {
		return !valuePerContext.isEmpty();
	}
}