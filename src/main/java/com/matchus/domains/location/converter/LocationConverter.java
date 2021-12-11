package com.matchus.domains.location.converter;

import com.matchus.domains.location.domain.City;
import com.matchus.domains.location.domain.Ground;
import com.matchus.domains.location.domain.Region;
import com.matchus.domains.location.dto.response.Location;
import com.matchus.domains.location.dto.response.LocationResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {

	public LocationResponse convertToLocationResponse(
		List<City> cities,
		List<Region> regions,
		List<Ground> grounds
	) {
		List<Location.City> cityList = new ArrayList<>();
		List<Location.Region> regionList = new ArrayList<>();
		List<Location.Ground> groundList = new ArrayList<>();

		for (City city : cities) {
			cityList.add(
				new Location.City(city.getId(), city.getName()));
		}
		for (Region region : regions) {
			regionList.add(
				new Location.Region(
					region.getId(),
					region.getCity().getId(),
					region.getName()
				));
		}
		for (Ground ground : grounds) {
			groundList.add(
				new Location.Ground(
					ground.getId(),
					ground.getRegion().getId(),
					ground.getName()
				));
		}

		return new LocationResponse(cityList, regionList, groundList);
	}
}
