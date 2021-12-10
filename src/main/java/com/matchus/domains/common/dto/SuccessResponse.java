package com.matchus.domains.common.dto;

import lombok.Getter;

@Getter
public class SuccessResponse {

	private final boolean isduplicated;

	public SuccessResponse(boolean isduplicated) {
		this.isduplicated = isduplicated;
	}
}
