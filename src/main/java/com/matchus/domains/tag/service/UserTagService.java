package com.matchus.domains.tag.service;

import com.matchus.domains.tag.domain.UserTag;
import com.matchus.domains.tag.repository.UserTagRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTagService {

	private final UserTagRepository userTagRepository;

	public UserTagService(UserTagRepository userTagRepository) {
		this.userTagRepository = userTagRepository;
	}

	@Transactional(readOnly = true)
	public List<UserTag> getUserTags(Long userId) {
		return userTagRepository.findAllByUserId(userId);
	}

}
