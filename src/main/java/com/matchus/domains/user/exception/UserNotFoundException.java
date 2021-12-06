package com.matchus.domains.user.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

	public UserNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public UserNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
