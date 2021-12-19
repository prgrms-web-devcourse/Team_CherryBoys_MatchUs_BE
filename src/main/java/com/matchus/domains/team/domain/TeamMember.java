package com.matchus.domains.team.domain;

import lombok.Getter;

@Getter
public class TeamMember {

	private final Long userId;
	private final String userNickname;
	private final String grade;

	public TeamMember(Long userId, String userNickname, String grade) {
		this.userId = userId;
		this.userNickname = userNickname;
		this.grade = grade;
	}
}
