package com.matchus.domains.user.dto;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.user.domain.Gender;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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

		@Builder
		public UserResponse(
			Long id,
			String sport,
			String email,
			String name,
			String bio,
			Gender gender,
			AgeGroup ageGroup,
			String roleGroup,
			BigDecimal mannerTemperature,
			List<UserGradeResponse> userGradeResponse
		) {
			this.id = id;
			this.sport = sport;
			this.email = email;
			this.name = name;
			this.bio = bio;
			this.gender = gender;
			this.ageGroup = ageGroup;
			this.roleGroup = roleGroup;
			this.mannerTemperature = mannerTemperature;
			this.userGradeResponse = userGradeResponse;
		}

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
