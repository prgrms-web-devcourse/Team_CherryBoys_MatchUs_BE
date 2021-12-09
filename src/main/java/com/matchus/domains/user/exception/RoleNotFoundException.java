package com.matchus.domains.user.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {

	public RoleNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
