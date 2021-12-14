package com.matchus.domains.team.service;

import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.repository.TeamUserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamUserService {

	private final TeamUserRepository teamUserRepository;

	public TeamUserService(TeamUserRepository teamUserRepository) {
		this.teamUserRepository = teamUserRepository;
	}

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

}
