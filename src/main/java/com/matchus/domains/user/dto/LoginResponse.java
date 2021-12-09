package com.matchus.domains.user.dto;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.user.domain.Gender;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class LoginResponse {

	private final String token;
	private final UserResponse userResponse;

	@Builder
	public LoginResponse(
		String token,
		UserResponse userResponse
	) {
		this.token = token;
		this.userResponse = userResponse;
	}


	@RequiredArgsConstructor
	@Builder
	@Getter
	public static class UserResponse {

		private final Long id;
		private final String sport;
		private final String email;
		private final String name;
		private final String bio;
		private final Gender gender;
		private final AgeGroup ageGroup;
		private final String roleGroup;
		private final BigDecimal mannerTemperature;
		private final List<UserGradeResponse> userGradeResponse;

	}

	@Getter
	public static class UserGradeResponse {

		private final Long teamId;
		private final Grade grade;

		public UserGradeResponse(Long teamId, Grade grade) {
			this.teamId = teamId;
			this.grade = grade;
		}

	}

}
