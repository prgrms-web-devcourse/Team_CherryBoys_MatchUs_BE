package com.matchus.domains.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {

	private String name;
	private String email;
	private String nickname;
	private String password;
	private String ageGroup;
	private String sports;
	private String gender;
	private String bio;

}
