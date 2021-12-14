package com.matchus.domains.match.dto.response;

import lombok.Getter;

@Getter
public class MatchWaitingTeam {

	private final Long teamWaitingId;

	private final MatchInfoResponse.TeamInfo teamInfo;

	public MatchWaitingTeam(
		Long teamWaitingId,
		MatchInfoResponse.TeamInfo teamInfo
	) {
		this.teamWaitingId = teamWaitingId;
		this.teamInfo = teamInfo;
	}

}
