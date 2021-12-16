package com.matchus.domains.hire.dto.response;

import com.matchus.domains.hire.domain.HireApplyTeam;
import java.util.List;
import lombok.Getter;

@Getter
public class HireApplicationTeamsResponse {

	private final List<HireApplyTeam> hireRequestTeams;

	public HireApplicationTeamsResponse(List<HireApplyTeam> hireRequestTeams) {
		this.hireRequestTeams = hireRequestTeams;
	}

}
