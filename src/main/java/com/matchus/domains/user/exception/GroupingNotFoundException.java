package com.matchus.domains.user.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class GroupingNotFoundException extends EntityNotFoundException {

	public GroupingNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
