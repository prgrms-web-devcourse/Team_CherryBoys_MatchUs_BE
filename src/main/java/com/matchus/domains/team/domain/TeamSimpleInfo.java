package com.matchus.domains.team.domain;

import lombok.Getter;

@Getter
public class TeamSimpleInfo {

	private final Long teamId;
	private final String teamName;

	public TeamSimpleInfo(Long teamId, String teamName) {
		this.teamId = teamId;
		this.teamName = teamName;
	}

}
