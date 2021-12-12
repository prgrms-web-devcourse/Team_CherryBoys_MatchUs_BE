package com.matchus.domains.user.controller;

import com.matchus.domains.common.dto.SuccessResponse;
import com.matchus.domains.user.dto.LoginRequest;
import com.matchus.domains.user.dto.LoginResponse;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.service.UserService;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@ApiOperation(
		value = "로그인 요청",
		notes = "회원가입한 사용자가 로그인을 요청합니다."
	)
	@PostMapping(path = "/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {

		return ResponseEntity.ok(ApiResponse.of(userService.login(request)));
	}

	@ApiOperation(
		value = "회원가입 요청",
		notes = "사용자가 회원가입을 요청합니다."
	)
	@PostMapping
	public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
		userService.signUp(request);
		return ResponseEntity
			.ok()
			.build();
	}

	@ApiOperation(
		value = "email 중복 확인",
		notes = "회원가입 정보 입력시 email 중복 여부를 확인합니다."
	)
	@GetMapping("/email-check/{email}")
	public ResponseEntity<ApiResponse<SuccessResponse>> checkEmail(@PathVariable String email) {
		return ResponseEntity.ok(ApiResponse.of(userService.checkEmail(email)));
	}

	@ApiOperation(
		value = "nickname 중복 확인",
		notes = "회원가입 정보 입력시 nickname 중복 여부를 확인합니다."
	)
	@GetMapping("/nickname-check/{nickname}")
	public ResponseEntity<ApiResponse<SuccessResponse>> checkNickname(@PathVariable String nickname) {
		return ResponseEntity.ok(ApiResponse.of(userService.checkNickname(nickname)));
	}

	@ApiOperation(
		value = "회원 탈퇴",
		notes = "가입된 계정을 탈퇴 처리합니다."
	)
	@DeleteMapping("/me")
	public ResponseEntity<Void> deactivateUser(
		@AuthenticationPrincipal JwtAuthentication authentication
	) {
		userService.deactivateUser(authentication.username);

		return ResponseEntity
			.ok()
			.build();
	}

	@ApiOperation(
		value = "새로고침시 유저 정보 재 요청",
		notes = "새로고침시 기존 로그인 되어있는 유저 정보를 재 요청합니다."
	)
	@GetMapping("/reissue")
	public ResponseEntity<ApiResponse<LoginResponse>> reissue(@AuthenticationPrincipal JwtAuthentication authentication) {

		return ResponseEntity.ok(
			ApiResponse.of(userService.reissue(authentication.username, authentication.token)));
	}

}
