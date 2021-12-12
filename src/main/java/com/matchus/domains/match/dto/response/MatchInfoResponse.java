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

	private final TeamInfoResponsse registerTeamResponsse;

	private TeamInfoResponsse applyTeamResponse;

	@Builder
	public MatchInfoResponse(
		Long matchId,
		String city,
		String region,
		String ground,
		LocalDate date, LocalTime startTime,
		LocalTime endTime,
		String ageGroup,
		MatchStatus status, int cost,
		String detail,
		TeamInfoResponsse registerTeamResponsse,
		TeamInfoResponsse applyTeamResponse
	) {
		this.matchId = matchId;
		this.city = city;
		this.region = region;
		this.ground = ground;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.ageGroup = ageGroup;
		this.status = status;
		this.cost = cost;
		this.detail = detail;
		this.registerTeamResponsse = registerTeamResponsse;
		this.applyTeamResponse = applyTeamResponse;
	}

	@Getter
	public static class TeamInfoResponsse {

		private long teamId;
		private String teamLogo;
		private String teamName;
		private Long captainId;
		private String captainName;
		private BigDecimal mannerTemperature;
		private List<MatchMember> matchMembers;

		public TeamInfoResponsse(
			long teamId,
			String teamLogo,
			String teamName,
			Long captainId,
			String captainName,
			BigDecimal mannerTemperature,
			List<MatchMember> matchMembers
		) {
			this.teamId = teamId;
			this.teamLogo = teamLogo;
			this.teamName = teamName;
			this.captainId = captainId;
			this.captainName = captainName;
			this.mannerTemperature = mannerTemperature;
			this.matchMembers = matchMembers;
		}

		public TeamInfoResponsse() {
		}

	}

}
