package com.matchus.domains.team.service;

import com.matchus.domains.team.domain.TeamInvitation;
import com.matchus.domains.team.exception.TeamInvitationNotFoundException;
import com.matchus.domains.team.repository.TeamInvitationRepository;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.response.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamInvitationService {

	private final TeamInvitationRepository teamInvitationRepository;
	private final TeamUserService teamUserService;

	public TeamInvitationService(
		TeamInvitationRepository teamInvitationRepository,
		TeamUserService teamUserService
	) {
		this.teamInvitationRepository = teamInvitationRepository;
		this.teamUserService = teamUserService;
	}

	@Transactional
	public SuccessResponse acceptTeamInvitation(Long invitationId) {
		TeamInvitation teamInvitation = findTeamInvitationById(invitationId);

		teamUserService.saveTeamUser(teamInvitation.getTeam(), teamInvitation.getUser());
		teamInvitationRepository.deleteById(invitationId);

		return new SuccessResponse(true);
	}

	@Transactional
	public SuccessResponse rejectTeamInvitation(Long invitationId) {
		teamInvitationRepository.delete(findTeamInvitationById(invitationId));

		return new SuccessResponse(true);
	}

	@Transactional(readOnly = true)
	public TeamInvitation findTeamInvitationById(Long invitationId) {

		return teamInvitationRepository
			.findById(invitationId)
			.orElseThrow(
				() -> new TeamInvitationNotFoundException(ErrorCode.TEAM_INVITATION_NOT_FOUND));
	}


}
