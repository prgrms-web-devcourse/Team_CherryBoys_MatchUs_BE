package com.matchus.domains.team.converter;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.dto.response.TeamInfoResponse;
import com.matchus.domains.user.domain.User;
import java.util.List;
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

	public TeamInfoResponse convertToTeamInfoResponse(Team team, List<String> tagNames, User user) {
		return new TeamInfoResponse(
			team.getId(),
			team.getName(),
			team.getBio(),
			team.getSport().getName(),
			team.getMatchCount(),
			team.getMannerTemperature(),
			team.getAgeGroup(),
			team.getCreatedDate(),
			user.getId(),
			user.getNickname(),
			tagNames
		);
	}
}
