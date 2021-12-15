package com.matchus.domains.tag.converter;

import com.matchus.domains.tag.domain.Tag;
import com.matchus.domains.tag.dto.response.TagResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TagConverter {

	public TagResponse convertToTagResponse(List<Tag> tags) {
		return new TagResponse(
			tags
				.stream()
				.map(
					tag ->
						new TagResponse.TagInfo(
							tag.getId(),
							tag.getName(),
							tag.getType().name()
						)
				)
				.collect(Collectors.toList())
		);
	}
}
