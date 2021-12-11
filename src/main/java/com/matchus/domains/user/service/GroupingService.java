package com.matchus.domains.user.service;

import com.matchus.domains.user.domain.Grouping;
import com.matchus.domains.user.exception.GroupingNotFoundException;
import com.matchus.domains.user.repository.GroupingRepository;
import com.matchus.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupingService {

	private final GroupingRepository groupingRepository;

	public GroupingService(
		GroupingRepository groupingRepository
	) {
		this.groupingRepository = groupingRepository;
	}

	@Transactional(readOnly = true)
	public Grouping findByName(String name) {

		return groupingRepository
			.findByName(name)
			.orElseThrow(() -> new GroupingNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}

}
