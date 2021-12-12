package com.matchus.domains.hire.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Min;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class HirePostWriteRequest {

	@Min(1)
	private final Long teamId;

	@Min(1)
	private final int hirePlayerNumber;

	@Min(1)
	private final Long cityId;

	@Min(1)
	private final Long regionId;

	@Min(1)
	private final Long groundId;
	private final String position;
	private final String ageGroup;
	private final String detail;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private final LocalDate date;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime startTime;

	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime endTime;

	public HirePostWriteRequest(
		Long teamId,
		int hirePlayerNumber,
		Long cityId,
		Long regionId,
		Long groundId,
		String position,
		String ageGroup,
		String detail,
		LocalDate date,
		LocalTime startTime,
		LocalTime endTime
	) {
		this.teamId = teamId;
		this.hirePlayerNumber = hirePlayerNumber;
		this.cityId = cityId;
		this.regionId = regionId;
		this.groundId = groundId;
		this.position = position;
		this.ageGroup = ageGroup;
		this.detail = detail;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
