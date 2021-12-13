package com.matchus.domains.match.repository;

import static com.matchus.domains.location.domain.QCity.*;
import static com.matchus.domains.location.domain.QGround.*;
import static com.matchus.domains.location.domain.QRegion.*;
import static com.matchus.domains.match.domain.QMatch.*;
import static com.matchus.domains.sports.domain.QSports.*;
import static com.matchus.domains.team.domain.QTeam.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.match.domain.MatchStatus;
import com.matchus.domains.match.dto.response.MatchListByFilterResponse;
import com.matchus.global.utils.PageRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;

public class MatchRepositoryImpl implements MatchRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public MatchRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<MatchListByFilterResponse> findAllNoOffsetByFilter(
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
					MatchListByFilterResponse.class,
					match.id.as("matchId"),
					match.city.name.as("city"),
					match.region.name.as("region"),
					match.ground.name.as("ground"),
					match.period.date.as("date"),
					match.period.startTime.as("startTime"),
					match.period.endTime.as("endTime"),
					match.ageGroup.as("ageGroup"),
					match.sport.name.as("sports"),
					team.id.as("teamId"),
					team.logo.as("teamLogo"),
					team.name.as("teamName"),
					team.mannerTemperature.as("teamMannerTemperature")
				)
			)
			.from(match)
			.join(match.homeTeam, team)
			.join(match.city, city)
			.join(match.region, region)
			.join(match.ground, ground)
			.join(match.sport, sports)
			.where(
				ltMatchId(pageRequest.getLastId()),
				eqSports(sportsId),
				eqAgeGroup(ageGroup),
				eqCity(cityId),
				eqRegion(regionId),
				eqGround(groundId),
				eqDate(date),
				match.status.eq(MatchStatus.WAITING)
			)
			.orderBy(match.id.desc())
			.limit(pageRequest.getSize())
			.fetch();
	}

	private BooleanExpression ltMatchId(Long matchId) {
		if (matchId == null) {
			return null;
		}
		return match.id.lt(matchId);
	}

	private BooleanExpression eqSports(Long sports) {
		if (sports == null) {
			return null;
		}
		return match.sport.id.eq(sports);
	}

	private BooleanExpression eqAgeGroup(AgeGroup ageGroup) {
		if (ageGroup == null) {
			return null;
		}
		return match.ageGroup.eq(ageGroup);
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
		return match.period.date.eq(date);
	}

}
