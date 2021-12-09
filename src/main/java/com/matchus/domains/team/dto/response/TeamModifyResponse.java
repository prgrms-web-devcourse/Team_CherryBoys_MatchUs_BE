package com.matchus.domains.team.dto.response;

import lombok.Getter;

@Getter
public class TeamModifyResponse {

	private final Long teamId;

	public TeamModifyResponse(Long teamId) {
		this.teamId = teamId;
	}
}
