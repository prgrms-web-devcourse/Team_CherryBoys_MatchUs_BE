package com.matchus.domains.team.exception;

import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.EntityNotFoundException;

public class TeamInvitationNotFoundException extends EntityNotFoundException {

	public TeamInvitationNotFoundException(
		String message,
		ErrorCode errorCode
	) {
		super(message, errorCode);
	}

	public TeamInvitationNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
