package com.practice.commerce.common.exception;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

	private LogLevel logLevel;

	public BaseException(String message, LogLevel logLevel) {
		super(message);
		this.logLevel = logLevel;
	}

	public abstract HttpStatus getHttpStatus();

	public abstract String getExceptionCode();

}
