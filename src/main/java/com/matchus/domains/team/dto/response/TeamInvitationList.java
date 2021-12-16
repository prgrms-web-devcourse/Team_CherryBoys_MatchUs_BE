package com.matchus.domains.team.dto.response;

import com.matchus.domains.team.domain.TeamInvitationInfo;
import java.util.List;
import lombok.Getter;

@Getter
public class TeamInvitationList {

	private final List<TeamInvitationInfo> teamInvitationInfos;

	public TeamInvitationList(List<TeamInvitationInfo> teamInvitationInfos) {
		this.teamInvitationInfos = teamInvitationInfos;
	}

}
