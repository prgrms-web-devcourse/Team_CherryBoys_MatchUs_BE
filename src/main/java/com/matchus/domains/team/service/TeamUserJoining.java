package com.matchus.domains.team.service;

import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.dto.response.TeamIdResponse;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TeamUserJoining {

	private final TeamUserService teamUserService;
	private final UserService userService;

	public TeamIdResponse leaveTeam(Long teamId, String userEmail) {
		User user = userService.findActiveUser(userEmail);
		TeamUser teamUser = teamUserService.getTeamUser(teamId, user.getId());
		teamUserService.delete(teamUser);

		return new TeamIdResponse(teamId);
	}
}
