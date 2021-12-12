package com.matchus.domains.team.domain;

import lombok.Getter;

@Getter
public class TeamMember {

	private final Long userId;
	private final String userName;
	private final Grade grade;

	public TeamMember(Long userId, String userName, Grade grade) {
		this.userId = userId;
		this.userName = userName;
		this.grade = grade;
	}
}
