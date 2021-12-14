package com.matchus.domains.user.dto.response;

import com.matchus.domains.team.domain.TeamSimpleInfo;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoResponse {

	private final String name;
	private final String nickname;
	private final String sportsName;
	private final String gender;
	private final String bio;
	private final String ageGrouop;
	private final List<String> tagNames;
	private final int matchCount;
	private final BigDecimal mannerTemperature;
	private final List<TeamSimpleInfo.TeamNameAndLogo> myTeams;

	@Builder
	public UserInfoResponse(
		String name,
		String nickname,
		String sportsName,
		String gender,
		String bio, String ageGrouop,
		List<String> tagNames,
		int matchCount,
		BigDecimal mannerTemperature,
		List<TeamSimpleInfo.TeamNameAndLogo> myTeams
	) {
		this.name = name;
		this.nickname = nickname;
		this.sportsName = sportsName;
		this.gender = gender;
		this.bio = bio;
		this.ageGrouop = ageGrouop;
		this.tagNames = tagNames;
		this.matchCount = matchCount;
		this.mannerTemperature = mannerTemperature;
		this.myTeams = myTeams;
	}

}
