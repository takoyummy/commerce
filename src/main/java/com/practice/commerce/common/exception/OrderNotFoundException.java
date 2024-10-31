package com.practice.commerce.common.exception;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseException {

	public OrderNotFoundException() {
		super("해당 주문을 찾지 못했습니다.", LogLevel.ERROR);
	}

	public OrderNotFoundException(String message) {
		super("해당 주문을 찾지 못했습니다. : " + message, LogLevel.ERROR);
	}

	public OrderNotFoundException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return null;
	}

	@Override
	public String getExceptionCode() {
		return null;
	}
}
