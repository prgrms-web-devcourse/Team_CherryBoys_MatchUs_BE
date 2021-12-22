package com.matchus.domains.hire.domain;

import lombok.Getter;

@Getter
public class HireApplyTeam {

	private final Long applicationId;
	private final Long hirePostId;
	private final Long teamId;
	private final String teamName;

	public HireApplyTeam(
		Long applicationId,
		Long hirePostId,
		Long teamId,
		String teamName
	) {
		this.applicationId = applicationId;
		this.hirePostId = hirePostId;
		this.teamId = teamId;
		this.teamName = teamName;
	}

}
