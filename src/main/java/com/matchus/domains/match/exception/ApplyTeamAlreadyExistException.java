package com.matchus.domains.match.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class ApplyTeamAlreadyExistException extends BusinessException {

	public ApplyTeamAlreadyExistException(
		String message,
		ErrorCode errorCode
	) {
		super(message, errorCode);
	}

	public ApplyTeamAlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}

}
