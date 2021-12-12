package com.matchus.domains.hire.dto.response;

import lombok.Getter;

@Getter
public class HirePostWriteResponse {

	private final Long postId;

	public HirePostWriteResponse(Long postId) {
		this.postId = postId;
	}
}
