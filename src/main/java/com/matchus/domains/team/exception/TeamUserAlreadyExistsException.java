package com.matchus.domains.team.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class TeamUserAlreadyExistsException extends BusinessException {

	public TeamUserAlreadyExistsException(
		String message,
		ErrorCode errorCode
	) {
		super(message, errorCode);
	}

	public TeamUserAlreadyExistsException(ErrorCode errorCode) {
		super(errorCode);
	}

}
