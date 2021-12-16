package com.matchus.domains.match.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class TeamTypeNotFoundException extends BusinessException {

	public TeamTypeNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public TeamTypeNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
