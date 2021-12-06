package com.matchus.domains.user.dto;

import com.matchus.domains.team.domain.Grade;
import lombok.Getter;

@Getter
public class UserGrade {

	private final Long teamId;
	private final Grade grade;

	public UserGrade(Long teamId, Grade grade) {
		this.teamId = teamId;
		this.grade = grade;
	}

}