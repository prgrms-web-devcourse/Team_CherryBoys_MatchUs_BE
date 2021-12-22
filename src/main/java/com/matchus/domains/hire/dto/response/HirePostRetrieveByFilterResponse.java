package com.matchus.domains.hire.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class HirePostRetrieveByFilterResponse {

	private final List<HirePostListFilterResponse> hirePosts;

	public HirePostRetrieveByFilterResponse(List<HirePostListFilterResponse> hirePosts) {
		this.hirePosts = hirePosts;
	}
}
