package com.matchus.domains.team.service;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.tag.domain.Tag;
import com.matchus.domains.tag.domain.TeamTag;
import com.matchus.domains.tag.service.TeamTagService;
import com.matchus.domains.team.converter.TeamConverter;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.domain.TeamMember;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.dto.request.TeamModifyRequest;
import com.matchus.domains.team.dto.response.TeamCreateResponse;
import com.matchus.domains.team.dto.response.TeamInfoResponse;
import com.matchus.domains.team.dto.response.TeamMatchInfo;
import com.matchus.domains.team.dto.response.TeamMatchesResponse;
import com.matchus.domains.team.dto.response.TeamMembersResponse;
import com.matchus.domains.team.dto.response.TeamModifyResponse;
import com.matchus.domains.team.exception.TeamNotFoundException;
import com.matchus.domains.team.repository.TeamRepository;
import com.matchus.domains.team.repository.TeamUserRepository;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.service.FileUploadService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamService {

	private final TeamConverter teamConverter;
	private final TeamRepository teamRepository;
	private final SportsService sportsService;
	private final FileUploadService uploadService;
	private final TeamTagService teamTagService;
	private final TeamUserRepository teamUserRepository;
	private final UserRepository userRepository;

	public TeamCreateResponse createTeam(TeamCreateRequest request, String userEmail) {
		String logo = uploadService.uploadImage(request.getLogo());
		Sports sports = sportsService
			.getSports(request.getSports());

		User user = userRepository
			.findByEmailAndIsDisaffiliatedFalse(userEmail)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

		Team createdTeam = teamRepository.save(
			teamConverter.convertToTeam(request, logo, sports)
		);
		TeamUser teamUser = TeamUser
			.builder()
			.team(createdTeam)
			.user(user)
			.grade(Grade.CAPTAIN)
			.build();
		teamUserRepository.save(teamUser);

		return new TeamCreateResponse(createdTeam.getId());
	}

	public TeamModifyResponse modifyTeam(Long teamId, TeamModifyRequest request) {
		String logo = uploadService.uploadImage(request.getLogo());
		Team team = findExistingTeam(teamId);
		team.changeInfo(logo, request.getBio(), AgeGroup.findGroup(request.getAgeGroup()));

		return new TeamModifyResponse(team.getId());
	}

	@Transactional(readOnly = true)
	public Team findExistingTeam(Long teamId) {
		return teamRepository
			.findByIdAndIsDeletedFalse(teamId)
			.orElseThrow(
				() -> new TeamNotFoundException(ErrorCode.ENTITY_NOT_FOUND)
			);
	}

	@Transactional(readOnly = true)
	public TeamInfoResponse getTeamInfo(Long teamId) {
		Team team = findExistingTeam(teamId);
		List<String> tagNames = teamTagService
			.getTeamTags(teamId)
			.stream()
			.sorted(Comparator.comparing(TeamTag::getTagCount).reversed())
			.map(TeamTag::getTag)
			.map(Tag::getName)
			.collect(Collectors.toList());

		User captain = teamUserRepository
			.findAllByTeamId(teamId)
			.stream()
			.filter(teamUser -> teamUser.getGrade().equals(Grade.CAPTAIN))
			.findFirst()
			.map(TeamUser::getUser)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

		return teamConverter.convertToTeamInfoResponse(team, tagNames, captain);
	}

	@Transactional(readOnly = true)
	public TeamMembersResponse getTeamMembers(Long teamId) {
		List<TeamUser> teamUsers = teamUserRepository.findAllByTeamIdAndGradeNot(
			teamId,
			Grade.HIRED
		);

		return new TeamMembersResponse(getTeamMembers(teamUsers));
	}

	@Transactional(readOnly = true)
	public TeamMembersResponse getTeamHiredMembers(Long teamId) {
		List<TeamUser> teamUsers = teamUserRepository.findAllByTeamIdAndGrade(
			teamId,
			Grade.HIRED
		);

		return new TeamMembersResponse(getTeamMembers(teamUsers));
	}

	@Transactional(readOnly = true)
	public TeamMatchesResponse getTeamMatches(Long teamId) {
		Team team = findExistingTeam(teamId);

		List<TeamMatchInfo> teamMatchInfos = new ArrayList<>();
		for (Match match : team.getAllMatches()) {
			teamMatchInfos.add(
				new TeamMatchInfo(
					match.getId(),
					match.getHomeTeam().getId(),
					match.getHomeTeam().getName(),
					match.getHomeTeam().getLogo(),
					match.getAwayTeam() != null ? match.getAwayTeam().getId() : null,
					match.getAwayTeam() != null ? match.getAwayTeam().getName() : null,
					match.getAwayTeam() != null ? match.getAwayTeam().getLogo() : null,
					match.getPeriod().getDate(),
					match.getStatus()
				)
			);
		}

		return new TeamMatchesResponse(teamMatchInfos);
	}

	private List<TeamMember> getTeamMembers(List<TeamUser> teamUsers) {
		List<TeamMember> teamMembers = new ArrayList<>();
		for (TeamUser teamUser : teamUsers) {
			User user = teamUser.getUser();
			teamMembers.add(new TeamMember(user.getId(), user.getName(), teamUser.getGrade()));
		}
		return teamMembers;
	}
}
