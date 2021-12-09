package com.matchus.domains.user.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class GroupNotFoundException extends EntityNotFoundException {

	public GroupNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
