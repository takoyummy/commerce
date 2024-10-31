package com.practice.commerce.common.exception;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import com.practice.commerce.common.StatusCode;

public class InventoryShortageException extends BaseException{
	public InventoryShortageException() {
		super("재고가 부족합니다.", LogLevel.ERROR);
	}

	public InventoryShortageException(String message) {
		super(message, LogLevel.ERROR);
	}

	public InventoryShortageException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.INVENTORY_SHORTAGE.getCode();
	}
}
