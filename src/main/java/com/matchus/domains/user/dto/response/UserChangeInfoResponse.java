package com.matchus.domains.user.dto.response;

import lombok.Getter;

@Getter
public class UserChangeInfoResponse {

	private final Long userId;

	public UserChangeInfoResponse(Long userId) {
		this.userId = userId;
	}

}
