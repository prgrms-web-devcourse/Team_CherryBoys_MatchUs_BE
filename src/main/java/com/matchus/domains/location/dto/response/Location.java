package com.matchus.domains.location.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class Location {

	private final List<Location.City> cities;
	private final List<Location.Region> regions;
	private final List<Location.Ground> grounds;

	public Location(
		List<Location.City> cities,
		List<Location.Region> regions,
		List<Location.Ground> grounds
	) {
		this.cities = cities;
		this.regions = regions;
		this.grounds = grounds;
	}

	@Getter
	public static class City {

		@JsonProperty(value = "cityId")
		private final Long id;

		@JsonProperty(value = "cityName")
		private final String name;

		public City(Long id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	@Getter
	public static class Region {

		@JsonProperty(value = "regionId")
		private final Long id;

		@JsonProperty(value = "regionName")
		private final String name;

		private final Long cityId;

		public Region(Long id, Long cityId, String name) {
			this.id = id;
			this.name = name;
			this.cityId = cityId;
		}
	}

	@Getter
	public static class Ground {

		@JsonProperty(value = "groundId")
		private final Long id;

		@JsonProperty(value = "groundName")
		private final String name;

		private final Long regionId;

		public Ground(Long id, Long regionId, String name) {
			this.id = id;
			this.name = name;
			this.regionId = regionId;
		}
	}
}
