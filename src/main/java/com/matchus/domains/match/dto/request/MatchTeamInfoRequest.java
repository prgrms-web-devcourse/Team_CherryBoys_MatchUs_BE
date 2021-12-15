package com.matchus.domains.match.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class MatchTeamInfoRequest {

	private final Long teamId;
	private final List<Long> players;

	public MatchTeamInfoRequest(
		Long teamId,
		List<Long> players
	) {
		this.teamId = teamId;
		this.players = players;
	}

}
