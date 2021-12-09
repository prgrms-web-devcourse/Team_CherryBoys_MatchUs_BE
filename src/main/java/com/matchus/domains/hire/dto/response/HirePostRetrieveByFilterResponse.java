package com.matchus.domains.hire.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class HirePostRetrieveByFilterResponse {

	private final List<HirePostListFilterResponseDto> hirePosts;

	public HirePostRetrieveByFilterResponse(List<HirePostListFilterResponseDto> hirePosts) {
		this.hirePosts = hirePosts;
	}
}
