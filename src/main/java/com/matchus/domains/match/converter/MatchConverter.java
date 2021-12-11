package com.matchus.domains.match.converter;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.MatchStatus;
import com.matchus.domains.match.dto.MatchCreateRequest;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.team.domain.Team;
import org.springframework.stereotype.Component;

@Component
public class MatchConverter {

	public Match convertToMatch(MatchCreateRequest request, Team team, Sports sport) {
		return Match
			.builder()
			.homeTeam(team)
			.sport(sport)
			.address(new Address(request.getCity(), request.getRegion(), request.getGroundName()))
			.period(new Period(request.getDate(), request.getStartTime(), request.getEndTime()))
			.ageGroup(AgeGroup.findGroup(request.getAgeGroup()))
			.cost(request.getCost())
			.detail(request.getDetail())
			.status(MatchStatus.WAITING)
			.build();
	}
}
