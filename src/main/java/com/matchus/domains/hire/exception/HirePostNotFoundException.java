package com.matchus.domains.hire.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class HirePostNotFoundException extends EntityNotFoundException {

	public HirePostNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public HirePostNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
