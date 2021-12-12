package com.matchus.domains.team.dto.response;

import com.matchus.domains.common.AgeGroup;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class TeamInfoResponse {

	private final Long teamId;
	private final String teamName;
	private final String bio;
	private final String sportsName;
	private final int matchCount;
	private final BigDecimal mannerTemperature;
	private final AgeGroup ageGroup;
	private final LocalDateTime teamCreatedAt;
	private final Long captainId;
	private final String captainName;
	private final List<String> tagNames;

	public TeamInfoResponse(
		Long teamId,
		String teamName,
		String bio,
		String sportsName,
		int matchCount,
		BigDecimal mannerTemperature,
		AgeGroup ageGroup,
		LocalDateTime teamCreatedAt,
		Long captainId,
		String captainName,
		List<String> tagNames
	) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.bio = bio;
		this.sportsName = sportsName;
		this.matchCount = matchCount;
		this.mannerTemperature = mannerTemperature;
		this.ageGroup = ageGroup;
		this.teamCreatedAt = teamCreatedAt;
		this.captainId = captainId;
		this.captainName = captainName;
		this.tagNames = tagNames;
	}
}
