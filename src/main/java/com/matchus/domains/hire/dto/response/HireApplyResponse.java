package com.matchus.domains.hire.dto.response;

import lombok.Getter;

@Getter
public class HireApplyResponse {

	private final Long applicationId;

	public HireApplyResponse(Long applicationId) {
		this.applicationId = applicationId;
	}
}
