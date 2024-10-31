package com.practice.commerce.common.exception;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

import com.practice.commerce.common.StatusCode;

public class MemberNotFoundException extends BaseException {

	public MemberNotFoundException() {
		super("멤버를 찾지 못했습니다.", LogLevel.ERROR);
	}

	public MemberNotFoundException(String message) {
		super(message, LogLevel.ERROR);
	}

	public MemberNotFoundException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.MEMBER_NOT_FOUND.getCode();
	}
}
