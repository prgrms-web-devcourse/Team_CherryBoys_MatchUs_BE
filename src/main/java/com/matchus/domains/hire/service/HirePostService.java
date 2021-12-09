package com.matchus.domains.hire.service;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.request.HirePostRetrieveFilterRequest;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponseDto;
import com.matchus.domains.hire.dto.response.HirePostRetrieveByFilterResponse;
import com.matchus.domains.hire.repository.HirePostRepository;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportService;
import com.matchus.global.utils.PageRequest;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class HirePostService {

	private final HirePostRepository hirePostRepository;
	private final SportService sportService;

	@Transactional(readOnly = true)
	public HirePostRetrieveByFilterResponse retrieveHirePostsNoOffsetByFilter(
		HirePostRetrieveFilterRequest filterRequest,
		PageRequest pageRequest
	) {
		Sports sports = sportService.getSportsOrNull(filterRequest.getSports());
		AgeGroup ageGroup = AgeGroup.findGroupOrNull(filterRequest.getAgeGroup());

		List<HirePostListFilterResponseDto> hirePosts = hirePostRepository.findAllNoOffsetByFilter(
			filterRequest.getPosition(),
			sports == null ? null : sports.getId(),
			ageGroup,
			filterRequest.getCity(),
			filterRequest.getRegion(),
			filterRequest.getGroundName(),
			filterRequest.getDate() == null ? null : LocalDate.parse(filterRequest.getDate()),
			pageRequest
		);

		return new HirePostRetrieveByFilterResponse(hirePosts);
	}

	@Transactional(readOnly = true)
	public List<HirePost> retrieveHirePostsNoOffset(
		Long lastId,
		int size
	) {
		return hirePostRepository.findAllNoOffset(lastId, size);
	}
}
