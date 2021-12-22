package com.matchus.domains.team.dto.request;

import com.matchus.domains.team.domain.TeamMember;
import java.util.List;
import lombok.Getter;

@Getter
public class ChangeGradesRequest {

	private List<TeamMember> members;

	public ChangeGradesRequest() {
	}

	public ChangeGradesRequest(List<TeamMember> members) {
		this.members = members;
	}
}
