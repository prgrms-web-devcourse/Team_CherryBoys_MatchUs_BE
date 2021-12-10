package com.matchus.domains.user.controller;

import com.matchus.domains.common.dto.SuccessResponse;
import com.matchus.domains.user.dto.LoginRequest;
import com.matchus.domains.user.dto.LoginResponse;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.service.UserService;
import com.matchus.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private final UserService userService;

	public UserController(
		UserService userService
	) {
		this.userService = userService;
	}

	@PostMapping(path = "/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {

		return ResponseEntity.ok(ApiResponse.of(userService.login(request)));
	}

	@PostMapping
	public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
		userService.signUp(request);
		return ResponseEntity
			.ok()
			.build();
	}

	@GetMapping("/email-check/{email}")
	public ResponseEntity<ApiResponse<SuccessResponse>> checkEmail(@PathVariable String email) {
		return ResponseEntity.ok(ApiResponse.of(userService.checkEmail(email)));
	}

	@GetMapping("/nickname-check/{nickname}")
	public ResponseEntity<ApiResponse<SuccessResponse>> checkNickname(@PathVariable String nickname) {
		return ResponseEntity.ok(ApiResponse.of(userService.checkNickname(nickname)));
	}

}
