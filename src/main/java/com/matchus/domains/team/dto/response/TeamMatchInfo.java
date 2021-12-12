package com.matchus.domains.team.dto.response;

import com.matchus.domains.match.domain.MatchStatus;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class TeamMatchInfo {

	private final Long matchId;
	private final Long registerTeamId;
	private final String registerTeamName;
	private final String registerTeamLogo;
	private final Long applyTeamId;
	private final String applyTeamName;
	private final String applyTeamLogo;
	private final LocalDate matchDate;
	private final MatchStatus status;

	public TeamMatchInfo(
		Long matchId,
		Long registerTeamId,
		String registerTeamName,
		String registerTeamlogo,
		Long applyTeamId,
		String applyTeamName,
		String applyTeamLogo,
		LocalDate matchDate,
		MatchStatus status
	) {
		this.matchId = matchId;
		this.registerTeamId = registerTeamId;
		this.registerTeamName = registerTeamName;
		this.registerTeamLogo = registerTeamlogo;
		this.applyTeamId = applyTeamId;
		this.applyTeamName = applyTeamName;
		this.applyTeamLogo = applyTeamLogo;
		this.matchDate = matchDate;
		this.status = status;
	}
}
