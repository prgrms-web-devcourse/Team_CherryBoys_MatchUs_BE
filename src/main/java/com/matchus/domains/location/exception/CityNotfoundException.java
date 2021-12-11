package com.matchus.domains.location.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class CityNotfoundException extends EntityNotFoundException {

	public CityNotfoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public CityNotfoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
