package com.matchus.domains.team.converter;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Sports;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.dto.request.TeamCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class TeamConverter {

	public Team convertToTeam(TeamCreateRequest request, String logo, Sports sports) {
		return Team
			.builder()
			.sport(sports)
			.name(request.getTeamName())
			.bio(request.getBio())
			.logo(logo)
			.ageGroup(AgeGroup.findGroup(request.getAgeGroup()))
			.build();
	}
}
