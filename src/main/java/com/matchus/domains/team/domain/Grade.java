package com.matchus.domains.team.domain;

import com.matchus.domains.team.exception.GradeNotFoundException;
import com.matchus.global.error.ErrorCode;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Grade {

	CAPTAIN("주장"),
	SUB_CAPTAIN("부주장"),
	GENERAL("일반"),
	HIRED("용병"),
	;

	private final String type;

	Grade(String type) {
		this.type = type;
	}

	public static Grade findGrade(String type) {
		return Arrays
			.stream(values())
			.filter(grade -> grade.getType().equals(type))
			.findFirst()
			.orElseThrow(() -> new GradeNotFoundException(ErrorCode.GRADE_NOT_FOUND));
	}
}
