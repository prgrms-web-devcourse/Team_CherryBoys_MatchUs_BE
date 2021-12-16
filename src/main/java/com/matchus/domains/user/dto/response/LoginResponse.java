package com.matchus.domains.user.dto.response;

import com.matchus.domains.team.domain.Grade;
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
		private final String name;
		private final String nickname;
		private final String bio;
		private final String sports;
		private final String gender;
		private final String ageGroup;
		private final String roleGroup;
		private final List<UserGradeResponse> userGradeResponse;

		@Builder
		public UserResponse(
			Long id,
			String name,
			String nickname,
			String bio,
			String sports,
			String gender,
			String ageGroup,
			String roleGroup,
			List<UserGradeResponse> userGradeResponse
		) {
			this.id = id;
			this.name = name;
			this.nickname = nickname;
			this.bio = bio;
			this.sports = sports;
			this.gender = gender;
			this.ageGroup = ageGroup;
			this.roleGroup = roleGroup;
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
