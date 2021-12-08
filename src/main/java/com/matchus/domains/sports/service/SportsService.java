package com.matchus.domains.sports.service;

import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.exception.SportsNotFoundException;
import com.matchus.domains.sports.repository.SportRepository;
import com.matchus.global.error.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class SportsService {

	private final SportRepository sportRepository;

	public SportsService(SportRepository sportRepository) {
		this.sportRepository = sportRepository;
	}

	public Sports getSports(String sportName) {

		return sportRepository
			.findByName(sportName)
			.orElseThrow(() -> new SportsNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

	}

}
