package com.matchus.domains.user.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class GenderNotFoundException extends BusinessException {

	public GenderNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public GenderNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
