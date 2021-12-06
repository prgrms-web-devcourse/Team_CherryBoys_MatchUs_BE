package com.matchus.domains.team.domain.service;

import com.matchus.domains.team.domain.repository.TeamUserReppository;
import com.matchus.domains.user.dto.UserGrade;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TeamUserService {

	private final TeamUserReppository teamUserReppository;

	public TeamUserService(TeamUserReppository teamUserReppository) {
		this.teamUserReppository = teamUserReppository;
	}

	public List<UserGrade> getUserGrades(Long userId) {
		return teamUserReppository
			.findAllByUserId(userId)
			.stream()
			.map(teamUser -> new UserGrade(teamUser
											   .getTeam()
											   .getId(), teamUser.getGrade()))
			.collect(Collectors.toList());
	}

}
