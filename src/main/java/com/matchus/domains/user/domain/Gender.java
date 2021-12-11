package com.matchus.domains.user.domain;

import com.matchus.domains.common.exception.GenderNotFoundException;
import com.matchus.global.error.ErrorCode;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Gender {

	MAN("남자"),
	WOMEN("여자"),
	OTHER("자유");

	private final String gender;

	Gender(String gender) {
		this.gender = gender;
	}

	public static Gender findGender(String name) {
		return Arrays
			.stream(values())
			.filter(gender -> gender
				.getGender()
				.equals(name))
			.findFirst()
			.orElseThrow(() -> new GenderNotFoundException(ErrorCode.GENDER_NOT_FOUND));
	}

}
