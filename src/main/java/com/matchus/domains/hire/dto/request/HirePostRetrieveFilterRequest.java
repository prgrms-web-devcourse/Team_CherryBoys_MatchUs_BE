package com.matchus.domains.hire.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HirePostRetrieveFilterRequest {

	private String position;
	private String sports;
	private String ageGroup;
	private Long cityId;
	private Long regionId;
	private Long groundId;
	private String date;

	public HirePostRetrieveFilterRequest() {

	}

	public HirePostRetrieveFilterRequest(
		String position,
		String sports,
		String ageGroup,
		Long cityId,
		Long regionId,
		Long groundId,
		String date
	) {
		this.position = position;
		this.sports = sports;
		this.ageGroup = ageGroup;
		this.cityId = cityId;
		this.regionId = regionId;
		this.groundId = groundId;
		this.date = date;
	}
}
