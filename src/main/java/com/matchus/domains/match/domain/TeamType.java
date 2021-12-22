package com.matchus.domains.match.domain;

import com.matchus.domains.match.exception.TeamTypeNotFoundException;
import com.matchus.global.error.ErrorCode;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum TeamType {

	REGISTER("등록팀"),
	APPLY("신청팀"),
	;

	private final String type;

	TeamType(String type) {
		this.type = type;
	}

	public static TeamType findTeamType(String type) {
		return Arrays
			.stream(values())
			.filter(teamType -> teamType.getType().equals(type))
			.findFirst()
			.orElseThrow(() -> new TeamTypeNotFoundException(ErrorCode.TEAM_TYPE_NOT_FOUND));
	}
}
