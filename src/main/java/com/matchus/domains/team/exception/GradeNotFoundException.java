package com.matchus.domains.team.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class GradeNotFoundException extends BusinessException {

	public GradeNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public GradeNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
