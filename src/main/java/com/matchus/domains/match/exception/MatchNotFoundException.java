package com.matchus.domains.match.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class MatchNotFoundException extends EntityNotFoundException {

	public MatchNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public MatchNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
