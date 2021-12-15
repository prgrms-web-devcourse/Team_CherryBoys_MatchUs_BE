package com.matchus.domains.match.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class MatchReviewRequest {

	private Long reviewerTeamId;
	private Long reviewedTeamId;
	private String reviewerTeamType;
	private List<Long> tagIds;

	public MatchReviewRequest() {
	}

	public MatchReviewRequest(
		Long reviewerTeamId,
		Long reviewedTeamId,
		String reviewedTeamType,
		List<Long> tagIds
	) {
		this.reviewerTeamId = reviewerTeamId;
		this.reviewedTeamId = reviewedTeamId;
		this.reviewerTeamType = reviewedTeamType;
		this.tagIds = tagIds;
	}
}
