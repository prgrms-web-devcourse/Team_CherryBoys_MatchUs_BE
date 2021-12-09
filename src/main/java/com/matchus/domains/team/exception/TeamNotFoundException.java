package com.matchus.domains.team.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class TeamNotFoundException extends EntityNotFoundException {

	public TeamNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public TeamNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
