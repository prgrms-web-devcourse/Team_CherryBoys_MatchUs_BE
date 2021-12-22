package com.matchus.domains.match.dto.response;

import lombok.Getter;

@Getter
public class MatchMember {

	private final Long userId;
	private final String userNickname;

	public MatchMember(Long userId, String userNickname) {
		this.userId = userId;
		this.userNickname = userNickname;
	}

}
