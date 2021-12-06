package com.matchus.domains.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {

	private final String grantType;
	private final String accessToken;
	private final String refreshToken;
	private final Long refreshTokenExpirationTime;

	@Builder
	public TokenResponse(
		String grantType,
		String accessToken,
		String refreshToken,
		Long refreshTokenExpirationTime
	) {
		this.grantType = grantType;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.refreshTokenExpirationTime = refreshTokenExpirationTime;
	}

}
