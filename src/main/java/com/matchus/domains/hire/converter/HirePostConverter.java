package com.matchus.domains.hire.converter;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.request.HirePostWriteRequest;
import com.matchus.domains.hire.dto.response.HirePostInfoResponse;
import com.matchus.domains.location.domain.Location;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.global.error.ErrorCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class HirePostConverter {

	public HirePostInfoResponse convertToHirePostInfoResponse(HirePost hirePost) {

		Long postId = hirePost.getId();
		String city = hirePost.getCity().getName();
		String region = hirePost.getRegion().getName();
		String groundName = hirePost.getGround().getName();
		String position = hirePost.getPosition();
		AgeGroup ageGroup = hirePost.getAgeGroup();
		int hirePlayerNumber = hirePost.getHirePlayerNumber();
		String detail = hirePost.getDetail();
		LocalDate date = hirePost.getPeriod().getDate();
		LocalTime startTime = hirePost.getPeriod().getStartTime();
		LocalTime endTIme = hirePost.getPeriod().getEndTime();

		Team team = hirePost.getTeam();
		Long teamId = team.getId();
		String teamName = team.getName();
		String teamLogo = team.getLogo();
		BigDecimal teamMannerTemperature = team.getMannerTemperature();
		TeamUser teamUser = hirePost
			.getTeam()
			.getTeamUsers()
			.stream()
			.filter(tUser -> tUser.getGrade() == Grade.CAPTAIN)
			.findFirst()
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
		Long teamCaptainId = teamUser.getUser().getId();
		String teamManagerName = teamUser.getUser().getName();

		return new HirePostInfoResponse(
			postId,
			city,
			region,
			groundName,
			position,
			ageGroup,
			hirePlayerNumber,
			detail,
			date,
			startTime,
			endTIme,
			teamId,
			teamName,
			teamLogo,
			teamMannerTemperature,
			teamCaptainId,
			teamManagerName
		);
	}

	public HirePost convertToHirePost(
		HirePostWriteRequest request,
		Location location,
		AgeGroup ageGroup
	) {
		return HirePost
			.builder()
			.position(request.getPosition())
			.city(location.getCity())
			.region(location.getRegion())
			.ground(location.getGround())
			.period(new Period(request.getDate(), request.getStartTime(), request.getEndTime()))
			.ageGroup(ageGroup)
			.detail(request.getDetail())
			.hirePlayerNumber(request.getHirePlayerNumber())
			.build();
	}
}
