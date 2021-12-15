package com.matchus.domains.match.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class MatchMemberModiftyRequest {

	private final Long teamId;
	private final List<Long> players;

	public MatchMemberModiftyRequest(
		Long teamId,
		List<Long> players
	) {
		this.teamId = teamId;
		this.players = players;
	}

}
