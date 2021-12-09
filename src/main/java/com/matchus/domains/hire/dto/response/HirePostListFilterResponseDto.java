package com.matchus.domains.hire.dto.response;

import com.matchus.domains.common.AgeGroup;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class HirePostListFilterResponseDto {

	private Long postId;
	private String position;
	private String city;
	private String region;
	private String groundName;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private AgeGroup ageGroup;
	private String detail;
	private int hirePlayerNumber;
	private Long teamId;
	private String teamLogo;
	private String teamName;
	private BigDecimal teamMannerTemperature;

	public HirePostListFilterResponseDto(
		Long postId,
		String position,
		String city,
		String region,
		String groundName,
		LocalDate date,
		LocalTime startTime,
		LocalTime endTime,
		AgeGroup ageGroup,
		String detail,
		int hirePlayerNumber,
		Long teamId,
		String teamLogo,
		String teamName,
		BigDecimal teamMannerTemperature
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
	}
}
