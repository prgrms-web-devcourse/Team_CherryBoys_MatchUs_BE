package com.matchus.domains.team.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class LowerGradeException extends BusinessException {

	public LowerGradeException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public LowerGradeException(ErrorCode errorCode) {
		super(errorCode);
	}

}
