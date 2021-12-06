package com.matchus.domains.user.service;

import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportService;
import com.matchus.domains.team.domain.service.TeamUserService;
import com.matchus.domains.user.UserConverter;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.LoginRequest;
import com.matchus.domains.user.dto.LoginResponse;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.dto.TokenResponse;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.UserNotFoundException;
import com.matchus.global.jwt.JwtTokenProvider;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TeamUserService teamUserService;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;
	private final SportService sportService;
	private final UserConverter userConverter;

	public UserService(
		UserRepository userRepository,
		AuthenticationManagerBuilder authenticationManagerBuilder,
		TeamUserService teamUserService,
		JwtTokenProvider jwtTokenProvider,
		RedisTemplate<String, String> redisTemplate,
		SportService sportService,
		UserConverter userConverter
	) {
		this.userRepository = userRepository;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.teamUserService = teamUserService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.redisTemplate = redisTemplate;
		this.sportService = sportService;
		this.userConverter = userConverter;
	}

	public void signUp(SignUpRequest signUpRequest) {
		Sports sports = sportService.getSports(signUpRequest.getName());

		userRepository.save(userConverter.convertToUser(signUpRequest, sports));

	}

	public LoginResponse login(LoginRequest login) {
		User user = userRepository
			.findByEmail(login.getEmail())
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			login.getEmail(), login.getPassword());

		Authentication authentication = authenticationManagerBuilder
			.getObject()
			.authenticate(authenticationToken);

		TokenResponse tokenInfo = jwtTokenProvider.generateToken(authentication);

		redisTemplate
			.opsForValue()
			.set(
				"RT:" + authentication.getName(), tokenInfo.getRefreshToken(),
				tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS
			);

		return new LoginResponse(tokenInfo, teamUserService.getUserGrades(user.getId()));

	}

}
