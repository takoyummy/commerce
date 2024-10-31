package com.practice.commerce.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKeyPrefix {
	BALANCE_KEY("user:balance:"),
	STOCK_KEY("product:stock:");

	private final String prefix;

	public String withSuffix(String suffix) {
		return prefix + suffix;
	}
}