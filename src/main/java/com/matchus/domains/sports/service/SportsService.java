package com.matchus.domains.sports.service;

import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.exception.SportsNotFoundException;
import com.matchus.domains.sports.repository.SportRepository;
import com.matchus.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SportsService {

	private final SportRepository sportRepository;

	public SportsService(SportRepository sportRepository) {
		this.sportRepository = sportRepository;
	}

	@Transactional(readOnly = true)
	public Sports getSports(String sportName) {

		return sportRepository
			.findByName(sportName)
			.orElseThrow(() -> new SportsNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

	}

	@Transactional(readOnly = true)
	public Sports getSportsOrNull(String sportName) {
		return sportRepository
			.findByName(sportName)
			.orElse(null);
	}
}
