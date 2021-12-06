package com.matchus.domains.team.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class TeamCreateRequest {

	private final MultipartFile logo;
	private final String teamName;
	private final String bio;
	private final String sports;
	private final String ageGroup;

	public TeamCreateRequest(
		MultipartFile logo,
		String teamName,
		String bio,
		String sports,
		String ageGroup
	) {
		this.logo = logo;
		this.teamName = teamName;
		this.bio = bio;
		this.sports = sports;
		this.ageGroup = ageGroup;
	}
}
