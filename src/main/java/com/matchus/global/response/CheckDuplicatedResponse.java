package com.matchus.global.response;

import lombok.Getter;

@Getter
public class CheckDuplicatedResponse {

	private final boolean isDuplicated;

	public CheckDuplicatedResponse(boolean isDuplicated) {
		this.isDuplicated = isDuplicated;
	}

}
