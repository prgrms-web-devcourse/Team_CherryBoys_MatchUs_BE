package com.matchus.domains.user;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.user.domain.Authority;
import com.matchus.domains.user.domain.Gender;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.SignUpRequest;
import java.util.Collections;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConverter {

	private final PasswordEncoder passwordEncoder;

	public UserConverter(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	public User convertToUser(SignUpRequest dto, Sports sports) {
		return User
			.builder()
			.name(dto.getName())
			.email(dto.getEmail())
			.password(passwordEncoder.encode(dto.getPassword()))
			.nickname(dto.getNickname())
			.bio(dto.getBio())
			.gender(Gender.valueOf(dto.getGender()))
			.ageGroup(AgeGroup.valueOf(dto.getAgeGroup()))
			.sport(sports)
			.roles(Collections.singletonList(Authority.ROLE_USER.name()))
			.build();
	}

}