package com.matchus.domains.match.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class InvalidTeamWaitingException extends BusinessException {

	public InvalidTeamWaitingException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public InvalidTeamWaitingException(ErrorCode errorCode) {
		super(errorCode);
	}

}
