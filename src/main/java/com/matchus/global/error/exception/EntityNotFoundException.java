package com.matchus.global.error.exception;

import com.matchus.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
