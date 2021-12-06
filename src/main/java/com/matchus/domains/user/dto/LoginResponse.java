package com.matchus.domains.user.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class LoginResponse {

	private final TokenResponse tokenResponse;
	private final List<UserGrade> userGrades;

	public LoginResponse(
		TokenResponse tokenResponse,
		List<UserGrade> userGrades
	) {
		this.tokenResponse = tokenResponse;
		this.userGrades = userGrades;
	}

}