package com.matchus.domains.match.dto;

import lombok.Getter;

@Getter
public class MatchMember {

	private final Long userId;
	private final String userName;

	public MatchMember(Long userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

}
