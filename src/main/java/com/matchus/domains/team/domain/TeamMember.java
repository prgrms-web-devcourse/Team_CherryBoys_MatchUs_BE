package com.matchus.domains.team.domain;

import lombok.Getter;

@Getter
public class TeamMember {

	private final Long userId;
	private final String userNickName;
	private final String grade;

	public TeamMember(Long userId, String userNickName, String grade) {
		this.userId = userId;
		this.userNickName = userNickName;
		this.grade = grade;
	}
}
