package com.matchus.domains.user.service;

import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(
		UserRepository userRepository,
		PasswordEncoder passwordEncoder
	) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly = true)
	public User loadUserByUserEmail(String principal, String credentials) {
		User user = this.userRepository
			.findByEmailAndIsDisaffiliatedFalse(principal)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
		user.checkPassword(passwordEncoder, credentials);
		return user;
	}

}

