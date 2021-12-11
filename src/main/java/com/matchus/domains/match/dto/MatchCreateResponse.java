package com.matchus.domains.match.dto;

import lombok.Getter;

@Getter
public class MatchCreateResponse {

	private final Long matchId;

	public MatchCreateResponse(Long matchId) {
		this.matchId = matchId;
	}

}
