package com.matchus.domains.match.service;

import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.domain.WaitingType;
import com.matchus.domains.match.repository.TeamWaitingReponsitory;
import com.matchus.domains.team.domain.Team;
import org.springframework.stereotype.Service;

@Service
public class TeamWaitingService {

	private final TeamWaitingReponsitory teamWaitingReponsitory;

	public TeamWaitingService(TeamWaitingReponsitory teamWaitingReponsitory) {
		this.teamWaitingReponsitory = teamWaitingReponsitory;
	}

	public TeamWaiting createTeamWaiting(Team team, Match match, WaitingType type) {

		return teamWaitingReponsitory.save(TeamWaiting
											   .builder()
											   .team(team)
											   .match(match)
											   .type(type)
											   .build());
	}
}
