package com.matchus.domains.user.dto;

import lombok.Getter;

@Getter
public class LoginRequest {

	private final String Email;

	private final String password;

	protected LoginRequest(String email, String password) {
		Email = email;
		this.password = password;
	}

}

