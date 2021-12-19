package com.matchus.domains.team.domain;

import lombok.Getter;

@Getter
public class TeamMember {

	private final Long userId;
	private final String userName;
	private final String grade;

	public TeamMember(Long userId, String userName, String grade) {
		this.userId = userId;
		this.userName = userName;
		this.grade = grade;
	}
}
