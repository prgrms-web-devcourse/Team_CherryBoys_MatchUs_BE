package com.matchus.domains.match.dto.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MatchReviewRequest {

	@Min(value = 1, message = "리뷰 등록 팀 정보가 누락되었습니다.")
	private Long reviewerTeamId;

	@Min(value = 1, message = "리뷰 받는 팀 정보가 누락되었습니다.")
	private Long reviewedTeamId;

	@NotBlank(message = "리뷰 등록 팀 타입 정보가 누락되었습니다.")
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
