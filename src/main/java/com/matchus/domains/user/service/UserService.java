package com.matchus.domains.user.service;

import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.user.converter.UserConverter;
import com.matchus.domains.user.domain.Group;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final SportsService sportsService;
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final GroupService groupService;

	public UserService(
		SportsService sportsService,
		UserRepository userRepository,
		UserConverter userConverter,
		GroupService groupService
	) {
		this.sportsService = sportsService;
		this.userRepository = userRepository;
		this.userConverter = userConverter;
		this.groupService = groupService;
	}

	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		Sports sports = sportsService.getSports(signUpRequest.getSports());
		Group group = groupService.findByName("USER_GROUP");

		User user = userRepository.save(userConverter.convertToUser(signUpRequest, sports, group));
	}

}
