package com.matchus.global.error.exception;

import com.matchus.global.error.ErrorCode;

public class InvalidFileTypeException extends BusinessException {

	public InvalidFileTypeException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public InvalidFileTypeException(ErrorCode errorCode) {
		super(errorCode);
	}
}
