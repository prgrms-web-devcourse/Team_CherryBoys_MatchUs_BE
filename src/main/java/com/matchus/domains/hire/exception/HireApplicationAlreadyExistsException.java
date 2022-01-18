package com.matchus.domains.hire.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class HireApplicationAlreadyExistsException extends BusinessException {

	public HireApplicationAlreadyExistsException(
		String message,
		ErrorCode errorCode
	) {
		super(message, errorCode);
	}

	public HireApplicationAlreadyExistsException(ErrorCode errorCode) {
		super(errorCode);
	}
}
