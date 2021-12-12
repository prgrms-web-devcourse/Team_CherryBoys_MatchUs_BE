package com.matchus.domains.hire.dto.response;

import lombok.Getter;

@Getter
public class HirePostModifyResponse {

	private final Long postId;

	public HirePostModifyResponse(Long postId) {
		this.postId = postId;
	}
}
