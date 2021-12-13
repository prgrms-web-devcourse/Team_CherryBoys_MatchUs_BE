package com.matchus.domains.hire.dto.request;

import lombok.Getter;

@Getter
public class HireApplyRequest {

	private Long postId;

	public HireApplyRequest() {
	}

	public HireApplyRequest(Long postId) {
		this.postId = postId;
	}
}
