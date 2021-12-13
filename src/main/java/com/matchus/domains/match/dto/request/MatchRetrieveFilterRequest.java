package com.matchus.domains.match.dto.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class MatchRetrieveFilterRequest {

	private String sports;

	private String ageGroup;

	private Long cityId;

	private Long regionId;

	private Long groundId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	public MatchRetrieveFilterRequest() {
	}

	public MatchRetrieveFilterRequest(
		String sports,
		String ageGroup,
		Long cityId,
		Long regionId,
		Long groundId,
		LocalDate date
	) {
		this.sports = sports;
		this.ageGroup = ageGroup;
		this.cityId = cityId;
		this.regionId = regionId;
		this.groundId = groundId;
		this.date = date;
	}

}
