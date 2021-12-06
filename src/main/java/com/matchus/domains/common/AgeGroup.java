package com.matchus.domains.common;

import com.matchus.domains.common.exception.AgeGroupNotFoundException;
import com.matchus.global.error.ErrorCode;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum AgeGroup {

	TEENAGER("10대"),
	TWENTIES("20대"),
	THIRTIES("30대"),
	FORTIES("40대"),
	FIFTIES("50대"),
	SIXTIES("60대"),
	;

	private final String ageGroup;

	AgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public static AgeGroup findGroup(String name) {
		return Arrays
			.stream(values())
			.filter(ageGroup -> ageGroup.getAgeGroup().equals(name))
			.findFirst()
			.orElseThrow(() -> new AgeGroupNotFoundException(ErrorCode.AGEGROUP_NOT_FOUND));
	}
}
