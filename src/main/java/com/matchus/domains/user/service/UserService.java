package com.matchus.domains.user.service;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.service.TeamUserService;
import com.matchus.domains.user.converter.UserConverter;
import com.matchus.domains.user.domain.Grouping;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.request.CheckDuplicatedResponse;
import com.matchus.domains.user.dto.request.LoginRequest;
import com.matchus.domains.user.dto.request.SignUpRequest;
import com.matchus.domains.user.dto.request.UserChangeInfoRequest;
import com.matchus.domains.user.dto.response.LoginResponse;
import com.matchus.domains.user.dto.response.UserChangeInfoResponse;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.jwt.JwtAuthenticationToken;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

	private final SportsService sportsService;
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final GroupingService groupingService;
	private final AuthenticationManager authenticationManager;
	private final TeamUserService teamUserService;
	private final PasswordEncoder passwordEncoder;

	public UserService(
		SportsService sportsService,
		UserRepository userRepository,
		UserConverter userConverter,
		GroupingService groupingService,
		AuthenticationManager authenticationManager,
		TeamUserService teamUserService,
		PasswordEncoder passwordEncoder
	) {
		this.sportsService = sportsService;
		this.userRepository = userRepository;
		this.userConverter = userConverter;
		this.groupingService = groupingService;
		this.authenticationManager = authenticationManager;
		this.teamUserService = teamUserService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		Sports sports = sportsService.getSports(signUpRequest.getSports());
		Grouping grouping = groupingService.findByName("USER_GROUP");
		String password = passwordEncoder.encode(signUpRequest.getPassword());

		User user = userRepository.save(
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

		List<LoginResponse.UserGradeResponse> userGrades = teamUserService.getUserGrades(
			user.getId());

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

	public User findActiveUser(String email) {
		return userRepository
			.findByEmailAndIsDisaffiliatedFalse(email)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}

	public User findUserByUserId(Long userId) {
		return userRepository
			.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}

	public LoginResponse reissue(String email, String token) {

		User user = findActiveUser(email);

		List<LoginResponse.UserGradeResponse> userGrades = teamUserService.getUserGrades(
			user.getId());

		return userConverter.convertToLoginResponse(user, token, userGrades);

	}

}
