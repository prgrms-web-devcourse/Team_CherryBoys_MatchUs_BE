package com.matchus.domains.match.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@RequiredArgsConstructor
public class MatchCreateRequest {

	private final Long registerTeamId;

	private final String sportsName;

	private final String city;

	private final String region;

	private final String groundName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private final LocalDate date;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime startTime;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime endTime;

	private final String ageGroup;

	private final int cost;

	private final String detail;

	private final List<Long> userIds;

}
