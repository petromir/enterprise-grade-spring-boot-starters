package com.petromirdzhunev.spring.boot.conversion.tabular;

import java.util.function.Function;

import org.springframework.util.Assert;

public record TabularColumn(Object context, int index, Function<String, String> valueTransformer) {

	public static final Function<String, String> REMOVE_ALL_EMPTY_SPACES = value -> value.replaceAll("\\s", "");

	public TabularColumn {
		if (index < 0) {
			throw new IllegalArgumentException("index must be greater than or equal to 0");
		}
		Assert.notNull(valueTransformer, "valueTransformer must not be null");
	}

	public TabularColumn(final Object context, final int index) {
		this(context, index, Function.identity());
	}
}