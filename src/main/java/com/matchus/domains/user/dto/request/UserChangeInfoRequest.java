package com.matchus.domains.user.dto.request;

import lombok.Getter;

@Getter
public class UserChangeInfoRequest {

	private String nickname;
	private String password;
	private String bio;
	private String ageGroup;
	private String sportName;

	public UserChangeInfoRequest() {
	}

	public UserChangeInfoRequest(
		String nickname,
		String password,
		String bio,
		String ageGroup,
		String sportName
	) {
		this.nickname = nickname;
		this.password = password;
		this.bio = bio;
		this.ageGroup = ageGroup;
		this.sportName = sportName;
	}

}
