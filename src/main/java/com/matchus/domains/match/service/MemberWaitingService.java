package com.matchus.domains.match.service;

import com.matchus.domains.match.domain.MemberWaiting;
import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.repository.MemberWaitingReponsitory;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberWaitingService {

	private final MemberWaitingReponsitory memberWaitingReponsitory;
	private final UserService userService;

	public MemberWaitingService(
		MemberWaitingReponsitory memberWaitingReponsitory,
		UserService userService
	) {
		this.memberWaitingReponsitory = memberWaitingReponsitory;
		this.userService = userService;
	}

	@Transactional
	public void saveMemberWaitings(List<Long> userIds, TeamWaiting teamWaiting) {

		for (Long id : userIds) {
			User user = userService.findUserByUserId(id);

			memberWaitingReponsitory.save(MemberWaiting
											  .builder()
											  .user(user)
											  .teamWaiting(teamWaiting)
											  .build());
		}

	}

	public List<MemberWaiting> getMemberWaitings(Long teamWaitingId) {
		return memberWaitingReponsitory.findAllByTeamWaitingId(teamWaitingId);
	}

	@Transactional
	public void removeAllMemberWaitings(Long teamWaitingId) {
		memberWaitingReponsitory.deleteAllByTeamWaitingId(teamWaitingId);
	}

}
