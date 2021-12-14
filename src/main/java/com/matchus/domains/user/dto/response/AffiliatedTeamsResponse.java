package com.matchus.domains.user.dto.response;

import com.matchus.domains.team.domain.TeamSimpleInfo;
import java.util.List;
import lombok.Getter;

@Getter
public class AffiliatedTeamsResponse {

	private final List<TeamSimpleInfo> teamSimpleInfos;

	public AffiliatedTeamsResponse(List<TeamSimpleInfo> teamSimpleInfos) {
		this.teamSimpleInfos = teamSimpleInfos;
	}

}
