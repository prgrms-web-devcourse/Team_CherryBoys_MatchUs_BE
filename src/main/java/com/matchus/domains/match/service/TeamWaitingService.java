package com.matchus.domains.match.service;

import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.domain.WaitingType;
import com.matchus.domains.match.exception.TeamWaitingNotFoundException;
import com.matchus.domains.match.repository.TeamWaitingReponsitory;
import com.matchus.domains.team.domain.Team;
import com.matchus.global.error.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamWaitingService {

	private final TeamWaitingReponsitory teamWaitingReponsitory;

	public TeamWaitingService(TeamWaitingReponsitory teamWaitingReponsitory) {
		this.teamWaitingReponsitory = teamWaitingReponsitory;
	}

	@Transactional
	public TeamWaiting createTeamWaiting(Team team, Match match, WaitingType type) {

		return teamWaitingReponsitory.save(TeamWaiting
											   .builder()
											   .team(team)
											   .match(match)
											   .type(type)
											   .build());
	}

	public TeamWaiting findByMatchIdAndType(Long matchId, WaitingType type) {
		return teamWaitingReponsitory
			.findByMatchIdAndType(matchId, type)
			.orElseThrow(() -> new TeamWaitingNotFoundException(ErrorCode.TEAM_WAITING_NOT_FOUND));
	}

	public List<TeamWaiting> findAllByMatchIdAndType(Long matchId, WaitingType type) {
		return teamWaitingReponsitory
			.findAllByMatchIdAndType(matchId, type);
	}

	public TeamWaiting findByIdAndTypeNot(Long teamWaitingId, WaitingType type) {
		return teamWaitingReponsitory
			.findByIdAndTypeNot(teamWaitingId, type)
			.orElseThrow(() -> new TeamWaitingNotFoundException(ErrorCode.TEAM_WAITING_NOT_FOUND));
	}

	public TeamWaiting findByMatchIdAndTeamId(Long matchId, Long teamId) {
		return teamWaitingReponsitory
			.findByMatchIdAndTeamId(matchId, teamId)
			.orElseThrow(() -> new TeamWaitingNotFoundException(ErrorCode.TEAM_WAITING_NOT_FOUND));
	}

	public TeamWaiting findByMatchAndTeam(Match match, Team reviewedTeam) {
		return teamWaitingReponsitory
			.findByMatchIdAndTeamId(
				match.getId(),
				reviewedTeam.getId()
			)
			.orElseThrow(() -> new TeamWaitingNotFoundException(ErrorCode.TEAM_WAITING_NOT_FOUND));
	}

}
