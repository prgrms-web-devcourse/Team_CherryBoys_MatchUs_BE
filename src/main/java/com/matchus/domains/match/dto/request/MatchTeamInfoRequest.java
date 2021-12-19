package com.matchus.domains.match.dto.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MatchTeamInfoRequest {

	@Min(value = 1, message = "매치 신청 팀 정보는 필수 입력 값입니다.")
	private final Long teamId;

	@Size(min = 1, message = "참여 팀원 정보는 필수 입력 값입니다.")
	private final List<Long> players;

	public MatchTeamInfoRequest(
		Long teamId,
		List<Long> players
	) {
		this.teamId = teamId;
		this.players = players;
	}

}
