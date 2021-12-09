package com.matchus.domains.hire.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HirePostRetrieveFilterRequest {

	private String position;
	private String sports;
	private String ageGroup;
	private String city;
	private String region;
	private String groundName;
	private String date;

	public HirePostRetrieveFilterRequest() {

	}

	public HirePostRetrieveFilterRequest(
		String position,
		String sports,
		String ageGroup,
		String city,
		String region,
		String groundName,
		String date
	) {
		this.position = position;
		this.sports = sports;
		this.ageGroup = ageGroup;
		this.city = city;
		this.region = region;
		this.groundName = groundName;
		this.date = date;
	}
}
