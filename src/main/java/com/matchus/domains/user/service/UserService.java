package com.matchus.domains.user.service;

import com.matchus.domains.common.dto.SuccessResponse;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.service.TeamUserService;
import com.matchus.domains.user.converter.UserConverter;
import com.matchus.domains.user.domain.Group;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.LoginRequest;
import com.matchus.domains.user.dto.LoginResponse;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.repository.UserRepository;
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
	private final GroupService groupService;
	private final AuthenticationManager authenticationManager;
	private final TeamUserService teamUserService;
	private final PasswordEncoder passwordEncoder;


	public UserService(
		SportsService sportsService,
		UserRepository userRepository,
		UserConverter userConverter,
		GroupService groupService,
		AuthenticationManager authenticationManager,
		TeamUserService teamUserService,
		PasswordEncoder passwordEncoder
	) {
		this.sportsService = sportsService;
		this.userRepository = userRepository;
		this.userConverter = userConverter;
		this.groupService = groupService;
		this.authenticationManager = authenticationManager;
		this.teamUserService = teamUserService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		Sports sports = sportsService.getSports(signUpRequest.getSports());
		Group group = groupService.findByName("USER_GROUP");
		String password = passwordEncoder.encode(signUpRequest.getPassword());

		User user = userRepository.save(
			userConverter.convertToUser(signUpRequest, sports, group, password));
	}

	public SuccessResponse checkEmail(String email) {
		boolean isExistence = userRepository.existsByEmail(email);
		return new SuccessResponse(isExistence);
	}

	public SuccessResponse checkNickname(String nickname) {
		boolean isExistence = userRepository.existsByNickname(nickname);
		return new SuccessResponse(isExistence);
	}

	public LoginResponse login(LoginRequest request) {
		JwtAuthenticationToken authToken = new JwtAuthenticationToken(
			request.getEmail(), request.getPassword());

		Authentication resultToken = authenticationManager.authenticate(authToken);
		JwtAuthentication authentication = (JwtAuthentication) resultToken.getPrincipal();

		User user = (User) resultToken.getDetails();

		List<LoginResponse.UserGradeResponse> userGrades = teamUserService.getUserGrades(
			user.getId());

		return userConverter.convertToLoginResponse(user, authentication, userGrades);
	}

}
