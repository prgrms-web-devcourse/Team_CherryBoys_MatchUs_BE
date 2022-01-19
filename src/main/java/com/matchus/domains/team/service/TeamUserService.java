package com.matchus.domains.team.service;

import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.exception.InsufficientGradeException;
import com.matchus.domains.team.exception.TeamUserAlreadyExistsException;
import com.matchus.domains.team.exception.TeamUserNotFoundException;
import com.matchus.domains.team.repository.TeamUserRepository;
import com.matchus.domains.user.domain.User;
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

	@Transactional(readOnly = true)
	public void validGrade(Long teamId, Long userId) {
		TeamUser teamUser = getTeamUser(teamId, userId);

		if (!Grade.VALID_GRADE.contains(teamUser.getGrade())) {
			throw new InsufficientGradeException(ErrorCode.UNAUTHORIZED_TEAM_USER);
		}
	}

	public TeamUser saveTeamUser(Team team, User user) {
		if (teamUserRepository.existsByUserAndTeam(user, team)) {
			throw new TeamUserAlreadyExistsException(ErrorCode.TEAM_USER_ALREADY_EXISTS);
		}
		return teamUserRepository.save(TeamUser
										   .builder()
										   .team(team)
										   .user(user)
										   .grade(Grade.GENERAL)
										   .build());
	}

	@Transactional(readOnly = true)
	public TeamUser getTeamUser(Long teamId, Long userId) {
		return teamUserRepository
			.findByTeamIdAndUserId(teamId, userId)
			.orElseThrow(() -> new TeamUserNotFoundException(ErrorCode.TEAM_USER_NOT_FOUND));
	}

	public void delete(TeamUser teamUser) {
		teamUserRepository.delete(teamUser);
	}

}
