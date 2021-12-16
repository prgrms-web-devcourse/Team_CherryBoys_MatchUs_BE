package com.matchus.domains.user.service;

import com.matchus.domains.match.domain.Match;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.domain.UserMatchHistory;
import com.matchus.domains.user.repository.UserMatchHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserMatchHistoryService {

	private final UserMatchHistoryRepository userMatchHistoryRepository;

	public UserMatchHistoryService(UserMatchHistoryRepository userMatchHistoryRepository) {
		this.userMatchHistoryRepository = userMatchHistoryRepository;
	}

	@Transactional
	public void saveUserMatchHistory(User user, Match match) {

		userMatchHistoryRepository.save(UserMatchHistory
											.builder()
											.user(user)
											.match(match)
											.build());
	}

}
