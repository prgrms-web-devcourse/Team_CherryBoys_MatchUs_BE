package com.matchus.domains.match.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class MatchWaitingListResponse {

	private final List<MatchWaitingTeam> matchWaitingListRespons;

	public MatchWaitingListResponse(List<MatchWaitingTeam> matchWaitingListRespons) {
		this.matchWaitingListRespons = matchWaitingListRespons;
	}

}
