package com.matchus.domains.user.controller;

import com.matchus.domains.team.service.TeamUserService;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.LoginRequest;
import com.matchus.domains.user.dto.LoginResponse;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.dto.UserGrade;
import com.matchus.domains.user.service.UserService;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.jwt.JwtAuthenticationToken;
import com.matchus.global.response.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final TeamUserService teamUserService;

	public UserController(
		UserService userService,
		AuthenticationManager authenticationManager,
		TeamUserService teamUserService
	) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.teamUserService = teamUserService;
	}

	@PostMapping(path = "/users/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
		JwtAuthenticationToken authToken = new JwtAuthenticationToken(
			request.getEmail(), request.getPassword());
		Authentication resultToken = authenticationManager.authenticate(authToken);
		JwtAuthentication authentication = (JwtAuthentication) resultToken.getPrincipal();
		User user = (User) resultToken.getDetails();

		List<UserGrade> userGrades = teamUserService.getUserGrades(user.getId());

		return ResponseEntity.ok(ApiResponse.of(LoginResponse
													.builder()
													.token(authentication.token)
													.group(user
															   .getGroup()
															   .getName())
													.userGrades(userGrades)
													.build()));
	}

	@PostMapping(path = "/users")
	public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
		userService.signUp(request);
		return ResponseEntity
			.ok()
			.build();
	}

}
