package com.matchus.domains.user.service;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.match.domain.MatchInfo;
import com.matchus.domains.match.domain.UserMatchHistory;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.tag.domain.Tag;
import com.matchus.domains.tag.domain.UserTag;
import com.matchus.domains.tag.service.UserTagService;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.TeamSimpleInfo;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.service.TeamUserService;
import com.matchus.domains.user.converter.UserConverter;
import com.matchus.domains.user.domain.Grouping;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.request.LoginRequest;
import com.matchus.domains.user.dto.request.SignUpRequest;
import com.matchus.domains.user.dto.request.UserChangeInfoRequest;
import com.matchus.domains.user.dto.response.AffiliatedTeamsResponse;
import com.matchus.domains.user.dto.response.LoginResponse;
import com.matchus.domains.user.dto.response.UserChangeInfoResponse;
import com.matchus.domains.user.dto.response.UserInfoResponse;
import com.matchus.domains.user.dto.response.UserMatchesResponse;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.jwt.JwtAuthenticationToken;
import java.util.ArrayList;
import com.matchus.global.response.CheckDuplicatedResponse;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	private final SportsService sportsService;
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final GroupingService groupingService;
	private final AuthenticationManager authenticationManager;
	private final TeamUserService teamUserService;
	private final PasswordEncoder passwordEncoder;
	private final UserTagService userTagService;

	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		Sports sports = sportsService.getSports(signUpRequest.getSports());
		Grouping grouping = groupingService.findByName("USER_GROUP");
		String password = passwordEncoder.encode(signUpRequest.getPassword());

		userRepository.save(
			userConverter.convertToUser(signUpRequest, sports, grouping, password));
	}

	public CheckDuplicatedResponse checkEmail(String email) {
		boolean isExistence = userRepository.existsByEmail(email);
		return new CheckDuplicatedResponse(isExistence);
	}

	public CheckDuplicatedResponse checkNickname(String nickname) {
		boolean isExistence = userRepository.existsByNickname(nickname);
		return new CheckDuplicatedResponse(isExistence);
	}

	public LoginResponse login(LoginRequest request) {
		JwtAuthenticationToken authToken = new JwtAuthenticationToken(
			request.getEmail(), request.getPassword());

		Authentication resultToken = authenticationManager.authenticate(authToken);
		JwtAuthentication authentication = (JwtAuthentication) resultToken.getPrincipal();

		User user = (User) resultToken.getDetails();

		List<LoginResponse.UserGradeResponse> userGrades = getUserGrades(user.getId());

		return userConverter.convertToLoginResponse(user, authentication.token, userGrades);
	}

	@Transactional
	public void deactivateUser(String email) {
		User user = findActiveUser(email);

		user.deactivateUser();
	}

	@Transactional
	public UserChangeInfoResponse changeInfoUser(UserChangeInfoRequest request, String email) {
		User user = findActiveUser(email);
		Sports sports = sportsService.getSports(request.getSportName());

		user.changeInfo(request.getNickname(), passwordEncoder.encode(request.getPassword()),
						request.getBio(),
						AgeGroup.findGroup(request.getAgeGroup()), sports
		);

		return new UserChangeInfoResponse(user.getId());
	}

	public LoginResponse reissue(String email, String token) {

		User user = findActiveUser(email);

		List<LoginResponse.UserGradeResponse> userGrades = getUserGrades(user.getId());

		return userConverter.convertToLoginResponse(user, token, userGrades);

	}

	public AffiliatedTeamsResponse getMyTeams(String email) {
		User user = findActiveUser(email);

		List<Grade> grades = Arrays.asList(Grade.CAPTAIN, Grade.SUB_CAPTAIN);
		List<TeamSimpleInfo.TeamName> teamSimpleInfoList = teamUserService
			.getMyTeamUsersByGrades(user.getId(), grades)
			.stream()
			.map(TeamUser::getTeam)
			.map(team -> new TeamSimpleInfo.TeamName(team.getId(), team.getName()))
			.collect(Collectors.toList());

		return new AffiliatedTeamsResponse(teamSimpleInfoList);
	}

	public UserInfoResponse getMyInfo(String email) {
		User user = findActiveUser(email);

		return buildUserInfoRespose(user);
	}

	public UserInfoResponse getUserInfo(Long id) {
		User user = findUserByUserId(id);

		return buildUserInfoRespose(user);
	}

	public UserMatchesResponse getUserMatches(Long userId) {
		User user = findUserByUserId(userId);

		List<MatchInfo> matchInfos = new ArrayList<>();

		for (UserMatchHistory userMatchHistory : user.getAllMatches()) {
			Match match = userMatchHistory.getMatch();
			matchInfos.add(
				new MatchInfo(
					match.getId(),
					match
						.getHomeTeam()
						.getId(),
					match
						.getHomeTeam()
						.getName(),
					match
						.getHomeTeam()
						.getLogo(),
					match
						.getAwayTeam()
						.getId(),
					match
						.getAwayTeam()
						.getName(),
					match
						.getAwayTeam()
						.getLogo(),
					match
						.getPeriod()
						.getDate(),
					match.getStatus()
				)
			);
		}

		return new UserMatchesResponse(matchInfos);
	}


	public User findActiveUser(String email) {
		return userRepository
			.findByEmailAndIsDisaffiliatedFalse(email)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
	}

	public User findUserByUserId(Long userId) {
		return userRepository
			.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
	}

	private List<LoginResponse.UserGradeResponse> getUserGrades(Long userId) {

		return teamUserService
			.getMyTeamUsers(userId)
			.stream()
			.map(teamUser -> new LoginResponse.UserGradeResponse(teamUser
																	 .getTeam()
																	 .getId(), teamUser.getGrade()))
			.collect(Collectors.toList());

	}

	private UserInfoResponse buildUserInfoRespose(User user) {

		List<String> tagNames = userTagService
			.getUserTags(user.getId())
			.stream()
			.sorted(Comparator
						.comparing(UserTag::getTagCount)
						.reversed())
			.map(UserTag::getTag)
			.map(Tag::getName)
			.collect(Collectors.toList());

		List<TeamSimpleInfo.TeamNameAndLogo> myTeams = teamUserService
			.getMyTeamUsers(user.getId())
			.stream()
			.map(teamUser -> teamUser.getTeam())
			.map(team -> new TeamSimpleInfo.TeamNameAndLogo(team.getId(), team.getName(),
															team.getLogo()
			))
			.collect(Collectors.toList());

		return userConverter.convertToUserInfoResponse(user, myTeams, tagNames);

	}

}
