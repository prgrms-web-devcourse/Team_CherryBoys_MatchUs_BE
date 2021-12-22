package com.matchus.domains.team.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.BusinessException;

public class TeamInvitationAlreadyExistsException extends BusinessException {

	public TeamInvitationAlreadyExistsException(
		String message,
		ErrorCode errorCode
	) {
		super(message, errorCode);
	}

	public TeamInvitationAlreadyExistsException(ErrorCode errorCode) {
		super(errorCode);
	}
}
