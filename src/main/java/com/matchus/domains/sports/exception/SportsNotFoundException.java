package com.matchus.domains.sports.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class SportsNotFoundException extends EntityNotFoundException {

	public SportsNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public SportsNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
