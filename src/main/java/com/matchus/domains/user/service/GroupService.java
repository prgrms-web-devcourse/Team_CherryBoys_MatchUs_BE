package com.matchus.domains.user.service;

import com.matchus.domains.user.domain.Group;
import com.matchus.domains.user.exception.GroupNotFoundException;
import com.matchus.domains.user.repository.GroupRepository;
import com.matchus.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupService {

	private final GroupRepository groupRepository;

	public GroupService(
		GroupRepository groupRepository
	) {
		this.groupRepository = groupRepository;
	}

	@Transactional(readOnly = true)
	public Group findByName(String name) {

		return groupRepository
			.findByName(name)
			.orElseThrow(() -> new GroupNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}

}
