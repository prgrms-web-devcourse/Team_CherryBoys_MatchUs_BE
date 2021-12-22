package com.matchus.domains.team.dto.response;

import com.matchus.domains.match.domain.MatchInfo;
import java.util.List;
import lombok.Getter;

@Getter
public class TeamMatchesResponse {

	private final List<MatchInfo> teamMatches;

	public TeamMatchesResponse(List<MatchInfo> teamMatches) {
		this.teamMatches = teamMatches;
	}
}
