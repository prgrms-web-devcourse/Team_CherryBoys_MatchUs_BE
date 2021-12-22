package com.matchus.domains.tag.service;

import com.matchus.domains.tag.converter.TagConverter;
import com.matchus.domains.tag.domain.Tag;
import com.matchus.domains.tag.dto.response.TagResponse;
import com.matchus.domains.tag.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagService {

	private final TagRepository tagRepository;
	private final TagConverter tagConverter;

	public TagResponse getTags() {
		List<Tag> tags = tagRepository.findAll();

		return tagConverter.convertToTagResponse(tags);
	}
}
