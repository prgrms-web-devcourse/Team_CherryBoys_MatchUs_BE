package com.matchus.domains.match.repository;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.match.dto.response.MatchListByFilterResult;
import com.matchus.global.utils.PageRequest;
import java.time.LocalDate;
import java.util.List;

public interface MatchRepositoryCustom {

	List<MatchListByFilterResult> findAllNoOffsetByFilter(
		Long sportsId,
		AgeGroup ageGroup,
		Long cityId,
		Long regionId,
		Long groundId,
		LocalDate date,
		PageRequest pageRequest
	);

}
