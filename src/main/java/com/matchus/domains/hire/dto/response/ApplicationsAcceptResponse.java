package com.matchus.domains.hire.dto.response;

import lombok.Getter;

@Getter
public class ApplicationsAcceptResponse {

	private Long hirePostId;

	public ApplicationsAcceptResponse() {

	}

	public ApplicationsAcceptResponse(Long hirePostId) {
		this.hirePostId = hirePostId;
	}
}
