package com.practice.commerce.common.exception;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import com.practice.commerce.common.StatusCode;

public class ProductNotFoundException extends BaseException {

	public ProductNotFoundException() {
		super("상품을 찾지 못했습니다.", LogLevel.ERROR);
	}

	public ProductNotFoundException(String message) {
		super("상품을 찾지 못했습니다. : " + message, LogLevel.ERROR);
	}

	public ProductNotFoundException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.ENTITY_NOT_FOUND.getCode();
	}
}
