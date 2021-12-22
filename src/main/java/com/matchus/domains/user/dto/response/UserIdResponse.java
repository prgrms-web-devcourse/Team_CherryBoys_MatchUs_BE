package com.matchus.domains.user.dto.response;

import lombok.Getter;

@Getter
public class UserIdResponse {

	private final Long userId;

	public UserIdResponse(Long userId) {
		this.userId = userId;
	}

}
