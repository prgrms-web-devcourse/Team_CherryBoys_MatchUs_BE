package com.matchus.domains.team.domain;

import lombok.Getter;

@Getter
public class TeamInvitationInfo {

	private final Long teamInvitationId;
	private final Long teamId;
	private final String teamName;

	public TeamInvitationInfo(Long teamInvitationId, Long teamId, String teamName) {
		this.teamInvitationId = teamInvitationId;
		this.teamId = teamId;
		this.teamName = teamName;
	}

}
