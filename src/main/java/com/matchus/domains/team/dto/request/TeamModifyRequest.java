package com.matchus.domains.team.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class TeamModifyRequest {

	private final MultipartFile logo;
	private final String bio;
	private final String ageGroup;

	public TeamModifyRequest(MultipartFile logo, String bio, String ageGroup) {
		this.logo = logo;
		this.bio = bio;
		this.ageGroup = ageGroup;
	}
}
