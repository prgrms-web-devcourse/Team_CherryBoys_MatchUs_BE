package com.matchus.global.jwt;

public class JwtAuthentication {

	public final String token;

	public final String username;

	public JwtAuthentication(
		String token,
		String username
	) {
		this.token = token;
		this.username = username;
	}

}
