package com.matchus.domains.location.domain;

import lombok.Getter;

@Getter
public class Location {

	private final City city;
	private final Region region;
	private final Ground ground;

	public Location(City city, Region region, Ground ground) {
		this.city = city;
		this.region = region;
		this.ground = ground;
	}

}
