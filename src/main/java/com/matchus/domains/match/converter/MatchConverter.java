package com.matchus.domains.match.converter;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.location.domain.Location;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.dto.request.MatchCreateRequest;
import com.matchus.domains.match.dto.response.MatchInfoResponse;
import com.matchus.domains.match.dto.response.MatchListByFilterResponse;
import com.matchus.domains.match.dto.response.MatchListByFilterResult;
import com.matchus.domains.match.dto.response.MatchRetrieveByFilterResponse;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.team.domain.Team;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MatchConverter {

	public Match convertToMatch(
		MatchCreateRequest request,
		Team team,
		Sports sport,
		Location location
	) {
		return Match
			.builder()
			.homeTeam(team)
			.sport(sport)
			.city(location.getCity())
			.region(location.getRegion())
			.ground(location.getGround())
			.period(new Period(request.getDate(), request.getStartTime(), request.getEndTime()))
			.ageGroup(AgeGroup.findGroup(request.getAgeGroup()))
			.cost(request.getCost())
			.detail(request.getDetail())
			.build();
	}

	public MatchInfoResponse convertToMatchInfoResponse(
		Match match,
		MatchInfoResponse.TeamInfo registerTeam,
		MatchInfoResponse.TeamInfo applyTeam
	) {

		return MatchInfoResponse
			.builder()
			.matchId(match.getId())
			.city(match
					  .getCity()
					  .getName())
			.region(match
						.getRegion()
						.getName())
			.ground(match
						.getGround()
						.getName())
			.sportName(match
						   .getSport()
						   .getName())
			.date(match
					  .getPeriod()
					  .getDate())
			.startTime(match
						   .getPeriod()
						   .getStartTime())
			.endTime(match
						 .getPeriod()
						 .getEndTime())
			.ageGroup(match
						  .getAgeGroup()
						  .getAgeGroup())
			.cost(match.getCost())
			.status(match.getStatus())
			.detail(match.getDetail())
			.registerTeamInfo(registerTeam)
			.applyTeamInfo(applyTeam)
			.build();

	}

	public MatchRetrieveByFilterResponse convertToRetrieveByFilterResponse(List<MatchListByFilterResult> results) {

		return new MatchRetrieveByFilterResponse(results
													 .stream()
													 .map(
														 result -> new MatchListByFilterResponse(
															 result.getMatchId(),
															 result.getCity(),
															 result.getRegion(),
															 result.getGround(),
															 result.getDate(),
															 result.getStartTime(),
															 result.getEndTime(),
															 result
																 .getAgeGroup()
																 .getAgeGroup(),
															 result.getSports(),
															 result.getTeamId(),
															 result.getTeamLogo(),
															 result.getTeamName(),
															 result.getMannerTemperature()
														 )
													 )
													 .collect(Collectors.toList()));
	}

}
