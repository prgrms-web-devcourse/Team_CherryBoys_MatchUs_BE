package com.matchus.domains.hire.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class HireApplicationNotFoundException extends EntityNotFoundException {

	public HireApplicationNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public HireApplicationNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
