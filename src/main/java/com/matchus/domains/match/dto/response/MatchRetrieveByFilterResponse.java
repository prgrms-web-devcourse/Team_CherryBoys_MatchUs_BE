package com.matchus.domains.match.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class MatchRetrieveByFilterResponse {

	private final List<MatchListByFilterResponse> matchList;

	public MatchRetrieveByFilterResponse(List<MatchListByFilterResponse> matchList) {
		this.matchList = matchList;
	}

}
