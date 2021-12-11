package com.matchus.domains.hire.repository;

import static com.matchus.domains.hire.domain.QHirePost.*;
import static com.matchus.domains.location.domain.QCity.*;
import static com.matchus.domains.location.domain.QGround.*;
import static com.matchus.domains.location.domain.QRegion.*;
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
		Long cityId,
		Long regionId,
		Long groundId,
		LocalDate date,
		PageRequest pageRequest
	) {
		return queryFactory
			.select(
				Projections.constructor(
					HirePostListFilterResponseDto.class,
					hirePost.id.as("postId"),
					hirePost.position.as("position"),
					city.name.as("city"),
					region.name.as("region"),
					ground.name.as("groundName"),
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
			.join(hirePost.city, city)
			.join(hirePost.region, region)
			.join(hirePost.ground, ground)
			.where(
				ltHirePostId(pageRequest.getLastId()),
				eqPosition(position),
				eqSports(sportsId),
				eqAgeGroup(ageGroup),
				eqCity(cityId),
				eqRegion(regionId),
				eqGround(groundId),
				eqDate(date)
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

	private BooleanExpression eqCity(Long cityId) {
		if (cityId == null) {
			return null;
		}
		return city.id.eq(cityId);
	}

	private BooleanExpression eqRegion(Long regionId) {
		if (regionId == null) {
			return null;
		}
		return region.id.eq(regionId);
	}

	private BooleanExpression eqGround(Long groundId) {
		if (groundId == null) {
			return null;
		}
		return ground.id.eq(groundId);
	}

	private BooleanExpression eqDate(LocalDate date) {
		if (date == null) {
			return null;
		}
		return hirePost.period.date.eq(date);
	}
}
