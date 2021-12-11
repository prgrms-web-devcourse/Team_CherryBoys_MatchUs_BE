package com.matchus.domains.match.service;

import com.matchus.domains.match.converter.MatchConverter;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.WaitingType;
import com.matchus.domains.match.dto.MatchCreateRequest;
import com.matchus.domains.match.dto.MatchCreateResponse;
import com.matchus.domains.match.repository.MatchRepository;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.service.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

	private final MatchRepository matchRepository;
	private final MatchConverter matchConverter;
	private final TeamService teamService;
	private final SportsService sportsService;
	private final MemberWaitingService memberWaitingService;

	public MatchService(
		MatchRepository matchRepository,
		MatchConverter matchConverter,
		TeamService teamService,
		SportsService sportsService,
		MemberWaitingService memberWaitingService
	) {
		this.matchRepository = matchRepository;
		this.matchConverter = matchConverter;
		this.teamService = teamService;
		this.sportsService = sportsService;
		this.memberWaitingService = memberWaitingService;
	}

	@Transactional
	public MatchCreateResponse createMatchPost(MatchCreateRequest matchCreateRequest) {
		Team registerTeam = teamService.findExistingTeam(matchCreateRequest.getRegisterTeamId());
		Sports sports = sportsService.getSports(matchCreateRequest.getSportsName());

		Match match = matchRepository.save(
			matchConverter
				.convertToMatch(
					matchCreateRequest,
					registerTeam,
					sports
				));

		memberWaitingService.saveMemberWaitings(registerTeam, matchCreateRequest.getUserIds(),
												match,
												WaitingType.REGISTER
		);

		return new MatchCreateResponse(match.getId());
	}

}
