package com.matchus.domains.team.service;

import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.dto.response.TeamIdResponse;
import com.matchus.domains.team.exception.TeamUserAlreadyExistsException;
import com.matchus.domains.team.exception.TeamUserNotFoundException;
import com.matchus.domains.team.repository.TeamUserRepository;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TeamUserService {

	private final TeamUserRepository teamUserRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public List<TeamUser> getMyTeamUsers(Long userId) {
		return teamUserRepository
			.findAllByUserId(userId);
	}

	@Transactional(readOnly = true)
	public List<TeamUser> getMyTeamUsersByGrades(Long userId, List<Grade> grades) {

		return teamUserRepository
			.findAllByUserIdAndGradeIn(userId, grades);

	}

	public void addHireMember(TeamUser teamUser) {
		teamUserRepository.save(teamUser);
	}

	public TeamIdResponse leaveTeam(Long teamId, String userEmail) {
		User user = userRepository
			.findByEmail(userEmail)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

		TeamUser teamUser = teamUserRepository
			.findByTeamIdAndUserId(teamId, user.getId())
			.orElseThrow(() -> new TeamUserNotFoundException(ErrorCode.TEAM_USER_NOT_FOUND));
		teamUserRepository.delete(teamUser);

		return new TeamIdResponse(teamId);
	}

	public TeamUser saveTeamUser(Team team, User user) {
		if (teamUserRepository.existsByUser(user)) {
			throw new TeamUserAlreadyExistsException(ErrorCode.TEAM_USER_ALREADY_EXISTS);
		}
		return teamUserRepository.save(TeamUser
										   .builder()
										   .team(team)
										   .user(user)
										   .grade(Grade.GENERAL)
										   .build());
	}

}
