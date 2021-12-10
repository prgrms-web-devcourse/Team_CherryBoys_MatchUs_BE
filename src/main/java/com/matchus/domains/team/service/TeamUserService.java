package com.matchus.domains.team.service;

import com.matchus.domains.team.repository.TeamUserReppository;
import com.matchus.domains.user.dto.LoginResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamUserService {

	private final TeamUserReppository teamUserReppository;

	public TeamUserService(TeamUserReppository teamUserReppository) {
		this.teamUserReppository = teamUserReppository;
	}

	@Transactional(readOnly = true)
	public List<LoginResponse.UserGradeResponse> getUserGrades(Long userId) {
		return teamUserReppository
			.findAllByUserId(userId)
			.stream()
			.map(teamUser -> new LoginResponse.UserGradeResponse(teamUser
																	 .getTeam()
																	 .getId(), teamUser.getGrade()))
			.collect(Collectors.toList());
	}

}
