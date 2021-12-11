package com.matchus.domains.location.service;

import com.matchus.domains.location.converter.LocationConverter;
import com.matchus.domains.location.dto.response.LocationResult;
import com.matchus.domains.location.repository.CityRepository;
import com.matchus.domains.location.repository.GroundRepository;
import com.matchus.domains.location.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocationService {

	private final CityRepository cityRepository;
	private final RegionRepository regionRepository;
	private final GroundRepository groundRepository;
	private final LocationConverter locationConverter;

	public LocationResult getLocations() {
		return locationConverter.convertToLocationResult(
			cityRepository.findAll(),
			regionRepository.findAll(),
			groundRepository.findAll()
		);
	}
}
