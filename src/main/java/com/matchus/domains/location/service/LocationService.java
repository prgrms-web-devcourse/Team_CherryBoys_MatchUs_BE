package com.matchus.domains.location.service;

import com.matchus.domains.location.domain.City;
import com.matchus.domains.location.domain.Ground;
import com.matchus.domains.location.domain.Location;
import com.matchus.domains.location.domain.Region;
import com.matchus.domains.location.exception.CityNotfoundException;
import com.matchus.domains.location.exception.GroundNotfoundException;
import com.matchus.domains.location.exception.RegionNotfoundException;
import com.matchus.domains.location.repository.CityRepository;
import com.matchus.domains.location.repository.GroundRepository;
import com.matchus.domains.location.repository.RegionRepository;
import com.matchus.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

	private final CityRepository cityRepository;
	private final RegionRepository regionRepository;
	private final GroundRepository groundRepository;

	public LocationService(
		CityRepository cityRepository,
		RegionRepository regionRepository,
		GroundRepository groundRepository
	) {
		this.cityRepository = cityRepository;
		this.regionRepository = regionRepository;
		this.groundRepository = groundRepository;
	}

	@Transactional(readOnly = true)
	public Location getLocation(Long cityId, Long regionId, Long groundId) {

		City city = cityRepository
			.findById(cityId)
			.orElseThrow(() -> new CityNotfoundException(ErrorCode.ENTITY_NOT_FOUND));

		Region region = regionRepository
			.findById(regionId)
			.orElseThrow(() -> new RegionNotfoundException(ErrorCode.ENTITY_NOT_FOUND));

		Ground ground = groundRepository
			.findById(groundId)
			.orElseThrow(() -> new GroundNotfoundException(ErrorCode.ENTITY_NOT_FOUND));

		return new Location(city, region, ground);

	}
}
