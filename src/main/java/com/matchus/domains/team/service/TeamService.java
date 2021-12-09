package com.matchus.domains.team.service;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.converter.TeamConverter;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.dto.request.TeamModifyRequest;
import com.matchus.domains.team.dto.response.TeamModifyResponse;
import com.matchus.domains.team.exception.TeamNotFoundException;
import com.matchus.domains.team.repository.TeamRepository;
import com.matchus.domains.team.repository.TeamUserRepository;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeamService {

	private final TeamConverter teamConverter;
	private final TeamRepository teamRepository;
	private final SportsService sportsService;
	private final FileUploadService uploadService;
	private final TeamUserRepository teamUserRepository;
	private final UserRepository userRepository;

	@Transactional
	public Team createTeam(TeamCreateRequest request) {
		String logo = uploadService.uploadImage(request.getLogo());
		Sports sports = sportsService
			.getSports(request.getSports());

		// todo: User 관련, 스프링 시큐리티 적용 시 수정 필요
		User user = userRepository
			.findById(1L)
			.get();

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

		return createdTeam;
	}

	@Transactional
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
}