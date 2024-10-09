package com.practice.commerce.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.practice.commerce.common.dto.ResponseMessage;
import com.practice.commerce.common.logger.ExceptionLogger;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final ExceptionLogger exceptionLogger;

	@Autowired
	public GlobalExceptionHandler(ExceptionLogger exceptionLogger) {
		this.exceptionLogger = exceptionLogger;
	}

	@ExceptionHandler(BaseException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseMessage handleBaseException(BaseException baseException, HttpServletRequest httpServletRequest) {

		exceptionLogger.log(baseException.getLogLevel(), baseException.getMessage(), baseException.getStackTrace(),
			httpServletRequest);

		return ResponseMessage.builder()
			.message(baseException.getMessage())
			.status(baseException.getHttpStatus().value())
			.statusCode(baseException.getExceptionCode())
			.build();
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseMessage handleOtherExceptions(Exception exception, HttpServletRequest httpServletRequest) {

		exceptionLogger.log(LogLevel.ERROR, exception.getMessage(), exception.getStackTrace(), httpServletRequest);

		return ResponseMessage.builder()
			.message(exception.getMessage())
			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.name())
			.build();
	}

}
