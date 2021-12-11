package com.matchus.domains.match.service;

import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.MemberWaiting;
import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.domain.WaitingType;
import com.matchus.domains.match.repository.MemberWaitingReponsitory;
import com.matchus.domains.match.repository.TeamWaitingReponsitory;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberWaitingService {

	private final MemberWaitingReponsitory memberWaitingReponsitory;
	private final TeamWaitingReponsitory teamWaitingReponsitory;
	private final UserRepository userRepository;


	public MemberWaitingService(
		MemberWaitingReponsitory memberWaitingReponsitory,
		TeamWaitingReponsitory teamWaitingReponsitory,
		UserRepository userRepository
	) {
		this.memberWaitingReponsitory = memberWaitingReponsitory;
		this.teamWaitingReponsitory = teamWaitingReponsitory;
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveMemberWaitings(Team team, List<Long> userIds, Match match, WaitingType type) {
		TeamWaiting teamWaiting = teamWaitingReponsitory.save(TeamWaiting
																  .builder()
																  .team(team)
																  .match(match)
																  .type(type)
																  .build());

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

}
