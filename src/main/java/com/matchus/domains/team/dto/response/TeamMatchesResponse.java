package com.matchus.domains.team.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class TeamMatchesResponse {

	private final List<TeamMatchInfo> teamMatches;

	public TeamMatchesResponse(List<TeamMatchInfo> teamMatches) {
		this.teamMatches = teamMatches;
	}
}
