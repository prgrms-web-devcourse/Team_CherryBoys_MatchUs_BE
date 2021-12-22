package com.matchus.domains.location.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class GroundNotfoundException extends EntityNotFoundException {

	public GroundNotfoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public GroundNotfoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
