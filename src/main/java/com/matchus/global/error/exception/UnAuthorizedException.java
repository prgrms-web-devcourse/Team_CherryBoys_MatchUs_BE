package com.matchus.global.error.exception;

import com.matchus.global.error.ErrorCode;

public class UnAuthorizedException extends BusinessException {

	public UnAuthorizedException(ErrorCode errorCode) {
		super(errorCode);
	}

}
