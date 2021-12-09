package com.matchus.domains.user.converter;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.user.domain.Gender;
import com.matchus.domains.user.domain.Group;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

	private final PasswordEncoder passwordEncoder;

	public UserConverter(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public User convertToUser(SignUpRequest dto, Sports sports, Group group) {
		return User
			.builder()
			.name(dto.getName())
			.email(dto.getEmail())
			.password(passwordEncoder.encode(dto.getPassword()))
			.nickname(dto.getNickname())
			.group(group)
			.bio(dto.getBio())
			.gender(Gender.valueOf(dto.getGender()))
			.ageGroup(AgeGroup.valueOf(dto.getAgeGroup()))
			.sport(sports)
			.build();
	}

}
