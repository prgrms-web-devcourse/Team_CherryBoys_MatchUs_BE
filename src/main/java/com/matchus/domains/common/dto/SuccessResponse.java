package com.matchus.domains.common.dto;

import lombok.Getter;

@Getter
public class SuccessResponse {

	private final boolean isSucceeded;

	public SuccessResponse(boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}

}
