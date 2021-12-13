package com.matchus.domains.match.dto.response;

import lombok.Getter;

@Getter
public class MatchIdResponse {

	private final Long matchId;

	public MatchIdResponse(Long matchId) {
		this.matchId = matchId;
	}

}
