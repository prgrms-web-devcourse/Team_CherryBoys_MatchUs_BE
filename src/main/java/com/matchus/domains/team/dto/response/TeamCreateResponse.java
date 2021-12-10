package com.matchus.domains.team.dto.response;

import lombok.Getter;

@Getter
public class TeamCreateResponse {

	private final Long teamId;

	public TeamCreateResponse(Long teamId) {
		this.teamId = teamId;
	}
}
