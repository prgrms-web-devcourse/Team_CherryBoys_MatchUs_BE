package com.matchus.domains.common.service;

import com.matchus.domains.common.Sports;
import com.matchus.domains.common.exception.SportsNotFoundException;
import com.matchus.domains.common.repository.SportsRepository;
import com.matchus.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SportsService {

	private final SportsRepository sportsRepository;

	@Transactional(readOnly = true)
	public Sports findByName(String name) {
		return sportsRepository
			.findByName(name)
			.orElseThrow(() -> new SportsNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}
}
