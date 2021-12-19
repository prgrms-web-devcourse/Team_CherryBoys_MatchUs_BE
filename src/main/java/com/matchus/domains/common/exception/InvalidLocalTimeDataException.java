package com.matchus.domains.common.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class InvalidLocalTimeDataException extends BusinessException {

	public InvalidLocalTimeDataException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public InvalidLocalTimeDataException(ErrorCode errorCode) {
		super(errorCode);
	}

}
