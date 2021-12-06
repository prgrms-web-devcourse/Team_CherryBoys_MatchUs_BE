package com.matchus.global.error.exception;


import com.matchus.global.error.ErrorCode;

public class UserNotFoundException extends BusinessException {

	public UserNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
