package com.matchus.domains.user.converter;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.team.domain.TeamSimpleInfo;
import com.matchus.domains.user.domain.Gender;
import com.matchus.domains.user.domain.Grouping;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.request.SignUpRequest;
import com.matchus.domains.user.dto.response.LoginResponse;
import com.matchus.domains.user.dto.response.UserInfoResponse;
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
			.gender(Gender.findGender(dto.getGender()))
			.ageGroup(AgeGroup.findGroup(dto.getAgeGroup()))
			.sport(sports)
			.build();
	}

	public LoginResponse convertToLoginResponse(
		User user,
		String token,
		List<LoginResponse.UserGradeResponse> userGrades
	) {

		LoginResponse.UserResponse userResponse = LoginResponse.UserResponse
			.builder()
			.id(user.getId())
			.name(user.getName())
			.roleGroup(user
						   .getGrouping()
						   .getName())
			.bio(user.getBio())
			.nickname(user.getNickname())
			.ageGroup(user.getAgeGroup())
			.gender(user.getGender())
			.sports(user
						.getSport()
						.getName())
			.userGradeResponse(userGrades)
			.build();

		return LoginResponse
			.builder()
			.token(token)
			.userResponse(userResponse)
			.build();
	}

	public UserInfoResponse convertToUserInfoResponse(
		User user,
		List<TeamSimpleInfo.TeamNameAndLogo> myTeams,
		List<String> tagNames
	) {

		return UserInfoResponse
			.builder()
			.name(user.getName())
			.nickname(user.getNickname())
			.sportsName(user
							.getSport()
							.getName())
			.gender(user
						.getGender()
						.getGender())
			.ageGrouop(user
						   .getAgeGroup()
						   .getAgeGroup())
			.tagNames(tagNames)
			.bio(user.getBio())
			.mannerTemperature(user.getMannerTemperature())
			.myTeams(myTeams)
			.matchCount(user.getMatchCount())
			.build();

	}

}
