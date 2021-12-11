package com.matchus.domains.location.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class LocationResponse {

	private final List<Location.City> cities;
	private final List<Location.Region> regions;
	private final List<Location.Ground> grounds;

	public LocationResponse(
		List<Location.City> cities,
		List<Location.Region> regions,
		List<Location.Ground> grounds
	) {
		this.cities = cities;
		this.regions = regions;
		this.grounds = grounds;
	}
}
