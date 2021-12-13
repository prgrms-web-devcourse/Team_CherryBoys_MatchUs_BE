package com.matchus.domains.team.dto.request;

import com.matchus.domains.team.domain.TeamMember;
import java.util.List;
import lombok.Getter;

@Getter
public class RemoveMembersRequest {

	private List<TeamMember> members;

	public RemoveMembersRequest() {
	}

	public RemoveMembersRequest(List<TeamMember> members) {
		this.members = members;
	}
}
