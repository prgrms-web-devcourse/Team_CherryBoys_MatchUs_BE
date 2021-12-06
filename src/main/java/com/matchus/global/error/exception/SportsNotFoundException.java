package com.matchus.global.error.exception;


import com.matchus.global.error.ErrorCode;

public class SportsNotFoundException extends BusinessException {

	public SportsNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
