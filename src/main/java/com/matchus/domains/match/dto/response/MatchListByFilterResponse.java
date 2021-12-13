package com.matchus.domains.match.dto.response;

import com.matchus.domains.common.AgeGroup;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MatchListByFilterResponse {

	private final Long matchId;

	private final String city;

	private final String region;

	private final String ground;

	private final LocalDate date;

	private final LocalTime startTime;

	private final LocalTime endTime;

	private final AgeGroup ageGroup;

	private final String sports;

	private final Long teamId;

	private final String teamLogo;

	private final String teamName;

	private final BigDecimal teamMannerTemperature;

}
