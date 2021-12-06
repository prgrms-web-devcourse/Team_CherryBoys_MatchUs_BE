package com.matchus.domains.user.service;

import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository usersRepository;

	public CustomUserDetailsService(UserRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usersRepository
			.findByEmail(username)
			.map((User users) -> createUserDetails(users))
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}

	private UserDetails createUserDetails(User users) {
		return new org.springframework.security.core.userdetails.User(
			users.getUsername(), users.getPassword(), users.getAuthorities());
	}
}