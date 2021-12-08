package com.matchus.domains.user.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

	private final String token;
	private final String group;
	private final List<UserGrade> userGrades;

	@Builder
	public LoginResponse(
		String token,
		String group,
		List<UserGrade> userGrades
	) {
		this.token = token;
		this.group = group;
		this.userGrades = userGrades;
	}

}
