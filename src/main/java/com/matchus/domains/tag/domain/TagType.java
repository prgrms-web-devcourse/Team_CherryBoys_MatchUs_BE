package com.matchus.domains.tag.domain;

import java.math.BigDecimal;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum TagType {

	GOOD(new BigDecimal("0.1"), temperature -> temperature.add(new BigDecimal("0.1"))),
	BAD(new BigDecimal("-0.1"), temperature -> temperature.subtract(new BigDecimal("0.1"))),
	NONE(new BigDecimal("0"), temperature -> temperature);

	private final BigDecimal value;
	private final Function<BigDecimal, BigDecimal> expression;

	TagType(BigDecimal value, Function<BigDecimal, BigDecimal> expression) {
		this.value = value;
		this.expression = expression;
	}

	public BigDecimal calculate(BigDecimal value) {
		return expression.apply(value);
	}
}
