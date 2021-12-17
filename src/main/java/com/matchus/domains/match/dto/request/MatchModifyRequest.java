package com.matchus.domains.match.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@RequiredArgsConstructor
public class MatchModifyRequest {

	private final Long city;

	private final Long region;

	private final Long ground;

	private final String sprotName;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private final LocalDate date;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime startTime;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime endTime;

	private final String ageGroup;

	private final int cost;

	private final String detail;

}
