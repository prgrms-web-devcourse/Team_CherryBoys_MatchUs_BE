package com.matchus.domains.match.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class TeamWaitingNotFoundException extends EntityNotFoundException {

	public TeamWaitingNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public TeamWaitingNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
