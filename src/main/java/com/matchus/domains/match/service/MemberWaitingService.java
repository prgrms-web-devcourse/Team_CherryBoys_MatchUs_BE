package com.matchus.domains.match.service;

import com.matchus.domains.match.domain.MemberWaiting;
import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.repository.MemberWaitingReponsitory;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberWaitingService {

	private final MemberWaitingReponsitory memberWaitingReponsitory;
	private final UserRepository userRepository;

	public MemberWaitingService(
		MemberWaitingReponsitory memberWaitingReponsitory,
		UserRepository userRepository
	) {
		this.memberWaitingReponsitory = memberWaitingReponsitory;
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveMemberWaitings(List<Long> userIds, TeamWaiting teamWaiting) {

		for (Long id : userIds) {
			User user = userRepository
				.findById(id)
				.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

			memberWaitingReponsitory.save(MemberWaiting
											  .builder()
											  .user(user)
											  .teamWaiting(teamWaiting)
											  .build());
		}

	}

	public List<MemberWaiting> getMemberWaitings(TeamWaiting teamWaiting) {
		return memberWaitingReponsitory.findAllByTeamWaitingId(teamWaiting.getId());
	}

}
