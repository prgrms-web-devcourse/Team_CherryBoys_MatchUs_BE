package com.matchus.domains.user.dto.response;

import com.matchus.domains.match.domain.MatchInfo;
import java.util.List;
import lombok.Getter;

@Getter
public class UserMatchesResponse {

	private final List<MatchInfo> userMatches;

	public UserMatchesResponse(List<MatchInfo> userMatches) {
		this.userMatches = userMatches;
	}

}
