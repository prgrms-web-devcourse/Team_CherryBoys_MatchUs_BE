package com.matchus.domains.hire.repository;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponseDto;
import com.matchus.global.utils.PageRequest;
import java.time.LocalDate;
import java.util.List;

public interface HirePostRepositoryCustom {

	List<HirePost> findAllNoOffset(Long lastId, int size);

	List<HirePostListFilterResponseDto> findAllNoOffsetByFilter(
		String position,
		Long sportsId,
		AgeGroup ageGroup,
		Long cityId,
		Long regionId,
		Long groundId,
		LocalDate date,
		PageRequest pageRequest
	);
}
