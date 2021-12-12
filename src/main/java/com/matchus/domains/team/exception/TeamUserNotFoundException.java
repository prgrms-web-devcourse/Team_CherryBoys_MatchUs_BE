package com.matchus.domains.team.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class TeamUserNotFoundException extends EntityNotFoundException {

	public TeamUserNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public TeamUserNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
