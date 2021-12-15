package com.matchus.domains.tag.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class TagNotFoundException extends EntityNotFoundException {


	public TagNotFoundException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public TagNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
