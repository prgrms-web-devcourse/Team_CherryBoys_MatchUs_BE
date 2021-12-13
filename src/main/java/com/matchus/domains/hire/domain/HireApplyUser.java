package com.matchus.domains.hire.domain;

import lombok.Getter;

@Getter
public class HireApplyUser {

	private final Long applicationId;
	private final Long userId;
	private final String userNickName;

	public HireApplyUser(Long applicationId, Long userId, String userNickName) {
		this.applicationId = applicationId;
		this.userId = userId;
		this.userNickName = userNickName;
	}
}
