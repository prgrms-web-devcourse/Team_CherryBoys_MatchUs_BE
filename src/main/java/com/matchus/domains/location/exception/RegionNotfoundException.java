package com.matchus.domains.location.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class RegionNotfoundException extends EntityNotFoundException {

	public RegionNotfoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public RegionNotfoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
