package com.matchus.domains.team.service;

import com.matchus.domains.team.domain.TeamInvitation;
import com.matchus.domains.team.domain.TeamInvitationInfo;
import com.matchus.domains.team.dto.response.TeamInvitationList;
import com.matchus.domains.team.exception.TeamInvitationNotFoundException;
import com.matchus.domains.team.repository.TeamInvitationRepository;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.service.UserService;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.response.SuccessResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamInvitationService {

	private final TeamInvitationRepository teamInvitationRepository;
	private final TeamUserService teamUserService;
	private final UserService userService;

	public TeamInvitationService(
		TeamInvitationRepository teamInvitationRepository,
		TeamUserService teamUserService,
		UserService userService
	) {
		this.teamInvitationRepository = teamInvitationRepository;
		this.teamUserService = teamUserService;
		this.userService = userService;
	}

	@Transactional
	public SuccessResponse acceptTeamInvitation(Long invitationId) {
		TeamInvitation teamInvitation = findTeamInvitationById(invitationId);

		teamUserService.saveTeamUser(teamInvitation.getTeam(), teamInvitation.getUser());
		teamInvitationRepository.deleteById(invitationId);

		return new SuccessResponse(true);
	}

	public TeamInvitation findTeamInvitationById(Long invitationId) {

		return teamInvitationRepository
			.findById(invitationId)
			.orElseThrow(
				() -> new TeamInvitationNotFoundException(ErrorCode.TEAM_INVITATION_NOT_FOUND));
	}

	public TeamInvitationList getTeamInvitations(String email) {
		User user = userService.findActiveUser(email);

		List<TeamInvitationInfo> teamInvitationInfos = teamInvitationRepository
			.findAllByUserId(user.getId())
			.stream()
			.map(teamInvitation -> new TeamInvitationInfo(
				teamInvitation.getId(),
				teamInvitation
					.getTeam()
					.getId(),
				teamInvitation
					.getTeam()
					.getName()
			))
			.collect(Collectors.toList());

		return new TeamInvitationList(teamInvitationInfos);
	}

}
