package com.matchus.domains.hire.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class InvalidHireApplicationException extends BusinessException {

	public InvalidHireApplicationException(
		String message,
		ErrorCode errorCode
	) {
		super(message, errorCode);
	}

	public InvalidHireApplicationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
