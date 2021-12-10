package com.matchus.domains.hire.repository;

import static com.matchus.domains.hire.domain.QHirePost.*;
import static com.matchus.domains.team.domain.QTeam.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponseDto;
import com.matchus.global.utils.PageRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class HirePostRepositoryImpl implements HirePostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<HirePost> findAllNoOffset(Long lastId, int size) {
		return queryFactory
			.selectFrom(hirePost)
			.where(ltHirePostId(lastId))
			.orderBy(hirePost.id.desc())
			.limit(size)
			.fetch();
	}

	@Override
	public List<HirePostListFilterResponseDto> findAllNoOffsetByFilter(
		String position,
		Long sportsId,
		AgeGroup ageGroup,
		String city,
		String region,
		String groundName,
		LocalDate date,
		PageRequest pageRequest
	) {
		return queryFactory
			.select(
				Projections.constructor(
					HirePostListFilterResponseDto.class,
					hirePost.id.as("postId"),
					hirePost.position.as("position"),
					hirePost.address.city.as("city"),
					hirePost.address.region.as("region"),
					hirePost.address.groundName.as("groundName"),
					hirePost.period.date.as("date"),
					hirePost.period.startTime.as("startTime"),
					hirePost.period.endTime.as("endTime"),
					hirePost.ageGroup.as("ageGroup"),
					hirePost.detail.as("detail"),
					hirePost.hirePlayerNumber.as("hirePlayerNumber"),
					team.id.as("teamId"),
					team.logo.as("teamLogo"),
					team.name.as("teamName"),
					team.mannerTemperature.as("teamMannerTemperature")
				)
			)
			.from(hirePost)
			.join(hirePost.team, team)
			.where(
				ltHirePostId(pageRequest.getLastId()),
				eqPosition(position),
				eqSports(sportsId),
				eqAgeGroup(ageGroup),
				eqCity(city),
				eqRegion(region),
				eqGroundName(groundName),
				eqDate(date),
				hirePost.isDeleted.isFalse()
			)
			.orderBy(hirePost.id.desc())
			.limit(pageRequest.getSize())
			.fetch();
	}

	private BooleanExpression ltHirePostId(Long hireId) {
		if (hireId == null) {
			return null;
		}
		return hirePost.id.lt(hireId);
	}

	private BooleanExpression eqPosition(String position) {
		if (StringUtils.isEmpty(position)) {
			return null;
		}
		return hirePost.position.eq(position);
	}

	private BooleanExpression eqSports(Long sports) {
		if (sports == null) {
			return null;
		}
		return team.sport.id.eq(sports);
	}

	private BooleanExpression eqAgeGroup(AgeGroup ageGroup) {
		if (ageGroup == null) {
			return null;
		}
		return hirePost.ageGroup.eq(ageGroup);
	}

	private BooleanExpression eqCity(String city) {
		if (StringUtils.isEmpty(city)) {
			return null;
		}
		return hirePost.address.city.eq(city);
	}

	private BooleanExpression eqRegion(String region) {
		if (StringUtils.isEmpty(region)) {
			return null;
		}
		return hirePost.address.region.eq(region);
	}

	private BooleanExpression eqGroundName(String groundName) {
		if (StringUtils.isEmpty(groundName)) {
			return null;
		}
		return hirePost.address.groundName.eq(groundName);
	}

	private BooleanExpression eqDate(LocalDate date) {
		if (date == null) {
			return null;
		}
		return hirePost.period.date.eq(date);
	}
}
