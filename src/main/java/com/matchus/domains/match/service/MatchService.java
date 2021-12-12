package com.matchus.domains.match.service;

import com.matchus.domains.location.domain.Location;
import com.matchus.domains.location.service.LocationService;
import com.matchus.domains.match.converter.MatchConverter;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.domain.WaitingType;
import com.matchus.domains.match.dto.MatchCreateRequest;
import com.matchus.domains.match.dto.MatchCreateResponse;
import com.matchus.domains.match.repository.MatchRepository;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

	private final MatchRepository matchRepository;
	private final MatchConverter matchConverter;
	private final TeamService teamService;
	private final SportsService sportsService;
	private final MemberWaitingService memberWaitingService;
	private final LocationService locationService;
	private final TeamWaitingService teamWaitingService;

	@Transactional
	public MatchCreateResponse createMatchPost(MatchCreateRequest matchCreateRequest) {
		Team registerTeam = teamService.findExistingTeam(matchCreateRequest.getRegisterTeamId());
		Sports sports = sportsService.getSports(matchCreateRequest.getSports());

		Location location = locationService.getLocation(
			matchCreateRequest.getCity(), matchCreateRequest.getRegion(),
			matchCreateRequest.getGround()
		);

		Match match = matchRepository.save(
			matchConverter
				.convertToMatch(
					matchCreateRequest,
					registerTeam,
					sports,
					location
				));

		TeamWaiting teamWaiting = teamWaitingService.createTeamWaiting(
			registerTeam, match, WaitingType.REGISTER);

		memberWaitingService.saveMemberWaitings(matchCreateRequest.getPlayers(), teamWaiting);

		return new MatchCreateResponse(match.getId());
	}

}
