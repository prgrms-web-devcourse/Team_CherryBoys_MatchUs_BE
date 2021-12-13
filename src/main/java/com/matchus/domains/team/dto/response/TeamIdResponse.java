package com.matchus.domains.team.dto.response;

import lombok.Getter;

@Getter
public class TeamIdResponse {

	private final Long teamId;

	public TeamIdResponse(Long teamId) {
		this.teamId = teamId;
	}
}
