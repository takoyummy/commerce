package com.practice.commerce.common.exception;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import com.practice.commerce.common.StatusCode;

import jdk.jshell.Snippet;

public class RedisLockException extends BaseException {

	public RedisLockException(String message) {
		super(message, LogLevel.ERROR);
	}

	public RedisLockException(String message, Throwable cause) {
		super(message, LogLevel.ERROR);
		this.initCause(cause);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.REDIS_LOCK_CONFLICT.getCode();
	}
}