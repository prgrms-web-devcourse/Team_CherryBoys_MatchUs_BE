package com.matchus.domains.common.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class AgeGroupNotFoundException extends BusinessException {

	public AgeGroupNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public AgeGroupNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
