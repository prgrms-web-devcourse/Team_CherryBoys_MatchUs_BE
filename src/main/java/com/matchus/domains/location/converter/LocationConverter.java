package com.matchus.domains.location.converter;

import com.matchus.domains.location.domain.City;
import com.matchus.domains.location.domain.Ground;
import com.matchus.domains.location.domain.Region;
import com.matchus.domains.location.dto.response.LocationResult;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {

	public LocationResult convertToLocationResult(
		List<City> cities,
		List<Region> regions,
		List<Ground> grounds
	) {
		List<LocationResult.City> cityList = new ArrayList<>();
		List<LocationResult.Region> regionList = new ArrayList<>();
		List<LocationResult.Ground> groundList = new ArrayList<>();

		for (City city : cities) {
			cityList.add(
				new LocationResult.City(city.getId(), city.getName()));
		}
		for (Region region : regions) {
			regionList.add(
				new LocationResult.Region(
					region.getId(),
					region.getCity().getId(),
					region.getName()
				));
		}
		for (Ground ground : grounds) {
			groundList.add(
				new LocationResult.Ground(
					ground.getId(),
					ground.getRegion().getId(),
					ground.getName()
				));
		}

		return new LocationResult(cityList, regionList, groundList);
	}
}
