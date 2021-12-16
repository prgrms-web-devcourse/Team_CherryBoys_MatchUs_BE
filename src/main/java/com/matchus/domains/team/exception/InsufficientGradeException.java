package com.matchus.domains.team.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class InsufficientGradeException extends BusinessException {

	public InsufficientGradeException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public InsufficientGradeException(ErrorCode errorCode) {
		super(errorCode);
	}

}
