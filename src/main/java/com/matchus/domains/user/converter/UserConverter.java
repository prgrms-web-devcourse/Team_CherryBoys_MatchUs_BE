package com.matchus.domains.user.converter;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.user.domain.Gender;
import com.matchus.domains.user.domain.Grouping;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.LoginResponse;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.global.jwt.JwtAuthentication;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

	public User convertToUser(
		SignUpRequest dto,
		Sports sports,
		Grouping grouping,
		String password
	) {
		return User
			.builder()
			.name(dto.getName())
			.email(dto.getEmail())
			.password(password)
			.nickname(dto.getNickname())
			.grouping(grouping)
			.bio(dto.getBio())
			.gender(Gender.findGender(dto.getGender()))
			.ageGroup(AgeGroup.findGroup(dto.getName()))
			.sport(sports)
			.build();
	}

	public LoginResponse convertToLoginResponse(
		User user,
		JwtAuthentication authentication,
		List<LoginResponse.UserGradeResponse> userGrades
	) {

		LoginResponse.UserResponse userResponse = LoginResponse.UserResponse
			.builder()
			.sport(user
					   .getSport()
					   .getName())
			.id(user.getId())
			.roleGroup(user
						   .getGrouping()
						   .getName())
			.bio(user.getBio())
			.ageGroup(user.getAgeGroup())
			.email(user.getEmail())
			.gender(user.getGender())
			.mannerTemperature(user.getMannerTemperature())
			.userGradeResponse(userGrades)
			.name(user.getName())
			.build();

		return LoginResponse
			.builder()
			.token(authentication.token)
			.userResponse(userResponse)
			.build();
	}


}
