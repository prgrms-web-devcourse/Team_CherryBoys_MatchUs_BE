package com.matchus.domains.tag.service;

import com.matchus.domains.tag.domain.Tag;
import com.matchus.domains.tag.domain.TagType;
import com.matchus.domains.tag.domain.UserTag;
import com.matchus.domains.tag.exception.TagNotFoundException;
import com.matchus.domains.tag.repository.TagRepository;
import com.matchus.domains.tag.repository.UserTagRepository;
import com.matchus.domains.user.domain.User;
import com.matchus.global.error.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserTagService {

	private final UserTagRepository userTagRepository;
	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
	public List<UserTag> getUserTags(Long userId) {
		return userTagRepository.findAllByUserId(userId);
	}

	public void calculateUserTags(List<User> waitingUsers, List<Long> tagIds) {
		for (User user : waitingUsers) {
			for (Long tagId : tagIds) {
				userTagRepository
					.findByUserIdAndTagId(user.getId(), tagId)
					.ifPresentOrElse(
						userTag -> {
							userTag.increaseTagCount();
							calculateUserMannerTemperature(
								userTag.getTag().getType(),
								user
							);
						},
						() -> {
							Tag tag = tagRepository
								.findById(tagId)
								.orElseThrow(() -> new TagNotFoundException(ErrorCode.TAG_NOT_FOUND));
							userTagRepository.save(
								UserTag
									.builder()
									.tag(tag)
									.user(user)
									.build()
							);
							calculateUserMannerTemperature(tag.getType(), user);
						}
					);
			}
		}
	}

	private void calculateUserMannerTemperature(TagType tagType, User user) {
		user.updateMannerTemperature(
			tagType.calculate(user.getMannerTemperature())
		);
	}
}
