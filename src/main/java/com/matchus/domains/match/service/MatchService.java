package com.matchus.domains.match.service;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.location.domain.Location;
import com.matchus.domains.location.service.LocationService;
import com.matchus.domains.match.converter.MatchConverter;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.MatchStatus;
import com.matchus.domains.match.domain.MemberWaiting;
import com.matchus.domains.match.domain.TeamType;
import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.domain.WaitingType;
import com.matchus.domains.match.dto.request.MatchCreateRequest;
import com.matchus.domains.match.dto.request.MatchModifyRequest;
import com.matchus.domains.match.dto.request.MatchRetrieveFilterRequest;
import com.matchus.domains.match.dto.request.MatchReviewRequest;
import com.matchus.domains.match.dto.request.MatchTeamInfoRequest;
import com.matchus.domains.match.dto.response.MatchIdResponse;
import com.matchus.domains.match.dto.response.MatchInfoResponse;
import com.matchus.domains.match.dto.response.MatchListByFilterResponse;
import com.matchus.domains.match.dto.response.MatchMember;
import com.matchus.domains.match.dto.response.MatchRetrieveByFilterResponse;
import com.matchus.domains.match.dto.response.MatchWaitingListResponse;
import com.matchus.domains.match.dto.response.MatchWaitingTeam;
import com.matchus.domains.match.exception.ApplyTeamAlreadyExistException;
import com.matchus.domains.match.exception.MatchNotFoundException;
import com.matchus.domains.match.repository.MatchRepository;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.tag.service.TeamTagService;
import com.matchus.domains.tag.service.UserTagService;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.exception.TeamUserNotFoundException;
import com.matchus.domains.team.service.TeamService;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.service.UserMatchHistoryService;
import com.matchus.domains.user.service.UserService;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.response.SuccessResponse;
import com.matchus.global.utils.PageRequest;
import java.util.ArrayList;
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
	private final TeamTagService teamTagService;
	private final UserTagService userTagService;
	private final UserService userService;
	private final UserMatchHistoryService userMatchHistoryService;

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

		createMatchWaiting(
			registerTeam, match, WaitingType.REGISTER, matchCreateRequest.getPlayers());

		return new MatchIdResponse(match.getId());
	}

	@Transactional
	public SuccessResponse removeMatch(String email, Long matchId) {

		Match match = findExistingMatch(matchId);
		userService.validTeamUser(match
									  .getHomeTeam()
									  .getId(), email);

		matchRepository.delete(match);
		return new SuccessResponse(true);
	}

	@Transactional
	public MatchIdResponse matchChangeInfo(MatchModifyRequest request, Long matchId) {
		Match match = findExistingMatch(matchId);

		Location location = locationService.getLocation(
			request.getCity(), request.getRegion(), request.getGround());

		Period period = new Period(request.getDate(), request.getStartTime(), request.getEndTime());

		match.changeInfo(
			location.getCity(), location.getRegion(), location.getGround(), period,
			request.getCost(), AgeGroup.findGroup(request.getAgeGroup()), request.getDetail()
		);

		return new MatchIdResponse(match.getId());
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

	public MatchInfoResponse getMatchInfo(Long matchId) {
		Match match = findExistingMatch(matchId);

		TeamWaiting registerTamWaiting = teamWaitingService.findByMatchIdAndType(
			match.getId(), WaitingType.REGISTER);

		MatchInfoResponse.TeamInfo regiserTeamInfo = buildTeamInfoResponse(
			registerTamWaiting);

		switch (match.getStatus()) {
			case WAITING:
				return matchConverter.convertToMatchInfoResponse(
					match, regiserTeamInfo, null);
			default:
				TeamWaiting applyTamWaiting = teamWaitingService.findByMatchIdAndType(
					match.getId(), WaitingType.SELECTED);

				return matchConverter.convertToMatchInfoResponse(
					match, regiserTeamInfo, buildTeamInfoResponse(applyTamWaiting));
		}
	}

	public MatchWaitingListResponse getMatchWaitingList(Long matchId) {

		List<TeamWaiting> teamWaitings = teamWaitingService.findAllByMatchIdAndType(
			matchId, WaitingType.WAITING);

		List<MatchWaitingTeam> matchWaitingTeams = new ArrayList<>();

		for (TeamWaiting teamWaiting : teamWaitings) {

			matchWaitingTeams.add(
				new MatchWaitingTeam(teamWaiting.getId(), buildTeamInfoResponse(teamWaiting)));

		}

		return new MatchWaitingListResponse(matchWaitingTeams);
	}

	@Transactional
	public MatchIdResponse changeMatchMembersInfo(MatchTeamInfoRequest request, Long matchId) {
		TeamWaiting teamWaiting = teamWaitingService.findByMatchIdAndTeamId(
			matchId, request.getTeamId()
		);

		memberWaitingService.removeAllMemberWaitings(teamWaiting.getId());

		memberWaitingService.saveMemberWaitings(request.getPlayers(), teamWaiting);

		return new MatchIdResponse(matchId);
	}

	@Transactional
	public MatchIdResponse acceptMatch(Long applyTeamWaitingId) {

		TeamWaiting teamWaiting = teamWaitingService.findByIdAndType(
			applyTeamWaitingId, WaitingType.WAITING);

		teamWaiting.changeWaitingType(WaitingType.SELECTED);

		Match match = teamWaiting.getMatch();

		if (match.getStatus() != MatchStatus.WAITING) {
			throw new ApplyTeamAlreadyExistException(ErrorCode.APPLY_TEAM_ALREADY_EXISTS);
		}

		match.achieveAwayTeam(teamWaiting.getTeam());

		recordUserMatchHistory(applyTeamWaitingId, match);

		TeamWaiting registerTeamWaiting = teamWaitingService.findByMatchIdAndType(
			match.getId(), WaitingType.REGISTER);
		recordUserMatchHistory(registerTeamWaiting.getId(), match);

		return new MatchIdResponse(match.getId());
	}

	@Transactional
	public MatchIdResponse applyMatch(Long matchId, MatchTeamInfoRequest request) {
		Match match = findExistingMatch(matchId);
		Team team = teamService.findExistingTeam(request.getTeamId());

		createMatchWaiting(team, match, WaitingType.WAITING, request.getPlayers());

		return new MatchIdResponse(matchId);
	}

	public Match findExistingMatch(Long matchId) {
		return matchRepository
			.findById(matchId)
			.orElseThrow(
				() -> new MatchNotFoundException(ErrorCode.ENTITY_NOT_FOUND)
			);
	}

	private void recordUserMatchHistory(Long teamWaitingId, Match match) {

		List<MemberWaiting> memberWaitings = memberWaitingService.getMemberWaitings(teamWaitingId);

		for (MemberWaiting memberWaiting : memberWaitings) {
			userMatchHistoryService.saveUserMatchHistory(memberWaiting.getUser(), match);
		}
	}

	@Transactional
	public MatchIdResponse reviewMatch(Long matchId, MatchReviewRequest request) {
		Match match = findExistingMatch(matchId);

		TeamType type = TeamType.findTeamType(request.getReviewerTeamType());
		if (type == TeamType.REGISTER) {
			match.completeHomeTeamReview();
		} else if (type == TeamType.APPLY) {
			match.completeAwayTeamReview();
		}

		if (match.isHomeTeamReviewed() && match.isAwayTeamReviewed()) {
			match.changeStatusReview();
		}

		Team reviewedTeam = teamService.findExistingTeam(request.getReviewedTeamId());
		teamTagService.calculateTeamTags(reviewedTeam, request.getTagIds());

		TeamWaiting teamWaiting = teamWaitingService.findByMatchIdAndTeamId(
			match.getId(),
			reviewedTeam.getId()
		);
		List<MemberWaiting> memberWaitings = memberWaitingService.getMemberWaitings(
			teamWaiting.getId());
		userTagService.calculateUserTags(memberWaitings, request.getTagIds());

		return new MatchIdResponse(matchId);
	}

	private void createMatchWaiting(Team team, Match match, WaitingType type, List<Long> players) {
		TeamWaiting teamWaiting = teamWaitingService.createTeamWaiting(team, match, type);

		memberWaitingService.saveMemberWaitings(players, teamWaiting);
	}

	private MatchInfoResponse.TeamInfo buildTeamInfoResponse(
		TeamWaiting teamWaiting
	) {

		Team team = teamWaiting.getTeam();

		List<MatchMember> matchMembers = memberWaitingService
			.getMemberWaitings(
				teamWaiting.getId())
			.stream()
			.map(memberWaiting -> new MatchMember(
				memberWaiting
					.getUser()
					.getId(),
				memberWaiting
					.getUser()
					.getName()
			))
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
