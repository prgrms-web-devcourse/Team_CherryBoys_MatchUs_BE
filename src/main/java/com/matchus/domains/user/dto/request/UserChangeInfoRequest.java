package com.matchus.domains.user.dto.request;

import lombok.Getter;

@Getter
public class UserChangeInfoRequest {

	private String nickname;
	private String bio;
	private String ageGroup;
	private String sportName;

	public UserChangeInfoRequest() {
	}

	public UserChangeInfoRequest(
		String nickname,
		String bio,
		String ageGroup,
		String sportName
	) {
		this.nickname = nickname;
		this.bio = bio;
		this.ageGroup = ageGroup;
		this.sportName = sportName;
	}

}
