package com.practice.commerce.common.exception;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import com.practice.commerce.common.StatusCode;

public class InsufficientBalanceException extends BaseException {
	public InsufficientBalanceException() {
		super("잔액이 충분하지 않습니다.", LogLevel.ERROR);
	}

	public InsufficientBalanceException(String message) {
		super(message, LogLevel.ERROR);
	}

	public InsufficientBalanceException(String message, LogLevel logLevel) {
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
