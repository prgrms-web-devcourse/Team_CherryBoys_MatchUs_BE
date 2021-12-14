package com.matchus.domains.team.service;

import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.repository.TeamUserRepository;
import com.matchus.domains.user.dto.response.LoginResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamUserService {

	private final TeamUserRepository teamUserRepository;

	public TeamUserService(TeamUserRepository teamUserRepository) {
		this.teamUserRepository = teamUserRepository;
	}

	@Transactional(readOnly = true)
	public List<LoginResponse.UserGradeResponse> getUserGrades(Long userId) {
		return teamUserRepository
			.findAllByUserId(userId)
			.stream()
			.map(teamUser -> new LoginResponse.UserGradeResponse(teamUser
																	 .getTeam()
																	 .getId(), teamUser.getGrade()))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<TeamUser> getMyTeamUsers(Long userId, List<Grade> grades) {

		return teamUserRepository
			.findAllByUserIdAndGradeIn(userId, grades);

	}

	public void addHireMember(TeamUser teamUser) {
		teamUserRepository.save(teamUser);
	}

}
