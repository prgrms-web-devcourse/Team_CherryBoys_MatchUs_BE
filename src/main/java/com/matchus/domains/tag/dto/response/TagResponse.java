package com.matchus.domains.tag.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class TagResponse {

	private final List<TagInfo> tags;

	public TagResponse(List<TagResponse.TagInfo> tags) {
		this.tags = tags;
	}

	@Getter
	public static class TagInfo {

		private final Long tagId;
		private final String tagName;
		private final String tagType;

		public TagInfo(Long tagId, String tagName, String tagType) {
			this.tagId = tagId;
			this.tagName = tagName;
			this.tagType = tagType;
		}
	}
}
