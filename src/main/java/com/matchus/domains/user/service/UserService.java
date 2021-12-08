package com.matchus.domains.user.service;

import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.user.UserConverter;
import com.matchus.domains.user.domain.Group;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final SportsService sportsService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final GroupService groupService;

	public UserService(
		SportsService sportsService,
		PasswordEncoder passwordEncoder,
		UserRepository userRepository,
		UserConverter userConverter,
		GroupService groupService
	) {
		this.sportsService = sportsService;
		this.passwordEncoder = passwordEncoder;
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

	@Transactional(readOnly = true)
	public User loadUserByUserEmail(String principal, String credentials) {
		User user = this.userRepository
			.findByEmail(principal)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
		user.checkPassword(passwordEncoder, credentials);
		return user;
	}


}
