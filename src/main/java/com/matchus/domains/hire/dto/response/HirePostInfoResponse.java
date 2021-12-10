package com.matchus.domains.hire.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.matchus.domains.common.AgeGroup;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class HirePostInfoResponse {

	private final Long postId;
	private final String city;
	private final String region;
	private final String groundName;
	private final String position;
	private final AgeGroup ageGroup;
	private final int hirePlayerNumber;
	private final String detail;
	private final LocalDate date;
	private final LocalTime startTime;
	private final LocalTime endTime;
	private final Long teamId;
	private final String teamName;
	private final String teamLogo;
	private final BigDecimal teamMannerTemperature;
	private final Long teamCaptainId;
	private final String teamCaptainName;

	public HirePostInfoResponse(
		Long postId,
		String title,
		String city,
		String region,
		String groundName,
		String position,
		AgeGroup ageGroup,
		int hirePlayerNumber,
		String detail,
		LocalDate date,
		LocalTime startTime,
		LocalTime endTime,
		Long teamId,
		String teamName,
		String teamLogo,
		BigDecimal teamMannerTemperature,
		Long teamCaptainId,
		String teamCaptainName
	) {
		this.postId = postId;
		this.position = position;
		this.city = city;
		this.region = region;
		this.groundName = groundName;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.ageGroup = ageGroup;
		this.detail = detail;
		this.hirePlayerNumber = hirePlayerNumber;
		this.teamId = teamId;
		this.teamLogo = teamLogo;
		this.teamName = teamName;
		this.teamMannerTemperature = teamMannerTemperature;
		this.teamCaptainId = teamCaptainId;
		this.teamCaptainName = teamCaptainName;
	}
}
