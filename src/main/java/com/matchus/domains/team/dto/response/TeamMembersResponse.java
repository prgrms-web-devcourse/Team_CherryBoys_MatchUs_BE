package com.matchus.domains.team.dto.response;

import com.matchus.domains.team.domain.TeamMember;
import java.util.List;
import lombok.Getter;

@Getter
public class TeamMembersResponse {

	private final List<TeamMember> members;

	public TeamMembersResponse(List<TeamMember> members) {
		this.members = members;
	}
}
