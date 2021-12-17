package com.matchus.domains.match.dto.response;

import com.matchus.domains.match.domain.MatchStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class MatchInfoResponse {

	private final Long matchId;

	private final String city;

	private final String region;

	private final String ground;

	private final String sportName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private final LocalDate date;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime startTime;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime endTime;

	private final String ageGroup;

	private final MatchStatus status;

	private final int cost;

	private final String detail;

	private final TeamInfo registerTeamInfo;

	private final TeamInfo applyTeamInfo;

	@Builder
	public MatchInfoResponse(
		Long matchId,
		String city,
		String region,
		String ground,
		String sportName,
		LocalDate date,
		LocalTime startTime,
		LocalTime endTime,
		String ageGroup,
		MatchStatus status, int cost,
		String detail,
		TeamInfo registerTeamInfo,
		TeamInfo applyTeamInfo
	) {
		this.matchId = matchId;
		this.city = city;
		this.region = region;
		this.ground = ground;
		this.sportName = sportName;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.ageGroup = ageGroup;
		this.status = status;
		this.cost = cost;
		this.detail = detail;
		this.registerTeamInfo = registerTeamInfo;
		this.applyTeamInfo = applyTeamInfo;
	}

	@Getter
	public static class TeamInfo {

		private final long teamId;
		private final String teamLogo;
		private final String teamName;
		private final Long captainId;
		private final String captainNickname;
		private final BigDecimal mannerTemperature;
		private final List<MatchMember> matchMembers;

		public TeamInfo(
			long teamId,
			String teamLogo,
			String teamName,
			Long captainId,
			String captainNickname,
			BigDecimal mannerTemperature,
			List<MatchMember> matchMembers
		) {
			this.teamId = teamId;
			this.teamLogo = teamLogo;
			this.teamName = teamName;
			this.captainId = captainId;
			this.captainNickname = captainNickname;
			this.mannerTemperature = mannerTemperature;
			this.matchMembers = matchMembers;
		}

	}

}
