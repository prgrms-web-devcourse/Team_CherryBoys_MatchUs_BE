package com.matchus.domains.team.domain;

import com.matchus.domains.team.exception.GradeNotFoundException;
import com.matchus.global.error.ErrorCode;
import java.util.Arrays;
import java.util.Set;
import lombok.Getter;

@Getter
public enum Grade {

	CAPTAIN("주장", 0),
	SUB_CAPTAIN("부주장", 1),
	GENERAL("일반", 2),
	HIRED("용병", 3),
	;

	public static final Set<Grade> VALID_GRADE = Set.of(CAPTAIN, SUB_CAPTAIN);

	private final String type;
	private final int order;

	Grade(String type, int order) {
		this.type = type;
		this.order = order;
	}

	public static Grade findGrade(String type) {
		return Arrays
			.stream(values())
			.filter(grade -> grade
				.getType()
				.equals(type))
			.findFirst()
			.orElseThrow(() -> new GradeNotFoundException(ErrorCode.GRADE_NOT_FOUND));
	}
}
