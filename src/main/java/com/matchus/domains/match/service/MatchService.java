package com.matchus.domains.match.service;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.location.domain.Location;
import com.matchus.domains.location.service.LocationService;
import com.matchus.domains.match.converter.MatchConverter;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.domain.WaitingType;
import com.matchus.domains.match.dto.request.MatchCreateRequest;
import com.matchus.domains.match.dto.request.MatchRetrieveFilterRequest;
import com.matchus.domains.match.dto.response.MatchIdResponse;
import com.matchus.domains.match.dto.response.MatchInfoResponse;
import com.matchus.domains.match.dto.response.MatchListByFilterResponse;
import com.matchus.domains.match.dto.response.MatchMember;
import com.matchus.domains.match.dto.response.MatchRetrieveByFilterResponse;
import com.matchus.domains.match.exception.MatchNotFoundException;
import com.matchus.domains.match.repository.MatchRepository;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.exception.TeamUserNotFoundException;
import com.matchus.domains.team.service.TeamService;
import com.matchus.domains.user.domain.User;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.utils.PageRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchService {

	private final MatchRepository matchRepository;
	private final MatchConverter matchConverter;
	private final TeamService teamService;
	private final SportsService sportsService;
	private final MemberWaitingService memberWaitingService;
	private final LocationService locationService;
	private final TeamWaitingService teamWaitingService;

	@Transactional
	public MatchIdResponse createMatchPost(MatchCreateRequest matchCreateRequest) {
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

		return new MatchIdResponse(match.getId());
	}

	public MatchInfoResponse getMatchInfo(Long matchId) {
		Match match = findExistingMatch(matchId);

		MatchInfoResponse.TeamInfo regiserTeamInfo = buildTeamInfoResponse(
			match, WaitingType.REGISTER);

		switch (match.getStatus()) {
			case WAITING:
				return matchConverter.convertToMatchInfoResponse(
					match, regiserTeamInfo, null);
			default:
				return matchConverter.convertToMatchInfoResponse(
					match, regiserTeamInfo, buildTeamInfoResponse(match, WaitingType.SELECTED));
		}
	}

	public MatchRetrieveByFilterResponse retrieveMatchNoOffsetByFilter(
		MatchRetrieveFilterRequest filterRequest,
		PageRequest pageRequest
	) {
		Sports sports = sportsService.getSportsOrNull(filterRequest.getSports());
		AgeGroup ageGroup = AgeGroup.findGroupOrNull(filterRequest.getAgeGroup());

		List<MatchListByFilterResponse> matchs = matchRepository.findAllNoOffsetByFilter(
			sports == null ? null : sports.getId(),
			ageGroup,
			filterRequest.getCityId(),
			filterRequest.getRegionId(),
			filterRequest.getGroundId(),
			filterRequest.getDate() == null ? null : filterRequest.getDate(),
			pageRequest
		);

		return new MatchRetrieveByFilterResponse(matchs);
	}

	@Transactional
	public MatchIdResponse acceptMatch(Long teamWaitingId) {

		TeamWaiting teamWaiting = teamWaitingService.findByIdAndTypeNot(
			teamWaitingId, WaitingType.REGISTER);

		teamWaiting.changeWaitingType(WaitingType.SELECTED);

		Match match = teamWaiting.getMatch();

		match.achieveAwayTeam(teamWaiting.getTeam());

		return new MatchIdResponse(match.getId());
	}

	public Match findExistingMatch(Long matchId) {
		return matchRepository
			.findById(matchId)
			.orElseThrow(
				() -> new MatchNotFoundException(ErrorCode.ENTITY_NOT_FOUND)
			);
	}

	private MatchInfoResponse.TeamInfo buildTeamInfoResponse(
		Match match,
		WaitingType waitingType
	) {

		TeamWaiting teamWaiting = teamWaitingService.findByMatchIdAndType(
			match.getId(), waitingType);

		Team team = teamWaiting.getTeam();

		List<MatchMember> matchMembers = memberWaitingService
			.getMemberWaitings(
				teamWaiting)
			.stream()
			.map(memberWaiting -> new MatchMember(
				memberWaiting
					.getUser()
					.getId(), memberWaiting
					.getUser()
					.getName()))
			.collect(Collectors.toList());

		User captain = team
			.getTeamUsers()
			.stream()
			.filter(member -> member
				.getGrade()
				.equals(Grade.CAPTAIN))
			.findFirst()
			.orElseThrow(() -> new TeamUserNotFoundException(ErrorCode.ENTITY_NOT_FOUND))
			.getUser();

		return new MatchInfoResponse.TeamInfo(
			team.getId(), team.getLogo(), team.getName(), captain.getId(),
			captain.getName(), team.getMannerTemperature(), matchMembers
		);

	}

}
