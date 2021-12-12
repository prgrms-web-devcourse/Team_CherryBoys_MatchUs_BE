package com.matchus.domains.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequest {

	private final String name;
	private final String email;
	private final String nickname;
	private final String password;
	private final String ageGroup;
	private final String sports;
	private final String gender;

	@Builder
	public SignUpRequest(
		String name,
		String email,
		String nickname,
		String password,
		String ageGroup,
		String sports,
		String gender
	) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.ageGroup = ageGroup;
		this.sports = sports;
		this.gender = gender;
	}

}
