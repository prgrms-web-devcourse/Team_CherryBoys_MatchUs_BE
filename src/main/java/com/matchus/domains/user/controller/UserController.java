package com.matchus.domains.user.controller;

import com.matchus.domains.user.dto.request.LoginRequest;
import com.matchus.domains.user.dto.request.SignUpRequest;
import com.matchus.domains.user.dto.request.UserChangeInfoRequest;
import com.matchus.domains.user.dto.response.AffiliatedTeamsResponse;
import com.matchus.domains.user.dto.response.LoginResponse;
import com.matchus.domains.user.dto.response.UserIdResponse;
import com.matchus.domains.user.dto.response.UserInfoResponse;
import com.matchus.domains.user.dto.response.UserMatchesResponse;
import com.matchus.domains.user.service.UserService;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.response.ApiResponse;
import com.matchus.global.response.CheckDuplicatedResponse;
import com.matchus.global.response.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<ApiResponse<SuccessResponse>> signUp(@RequestBody SignUpRequest request) {
		userService.signUp(request);
		return ResponseEntity.ok(ApiResponse.of(new SuccessResponse(true)));
	}

	@ApiOperation(
		value = "email 중복 확인",
		notes = "회원가입 정보 입력시 email 중복 여부를 확인합니다."
	)
	@GetMapping("/email-check/{email}")
	public ResponseEntity<ApiResponse<CheckDuplicatedResponse>> checkEmail(@PathVariable String email) {
		return ResponseEntity.ok(ApiResponse.of(userService.checkEmail(email)));
	}

	@ApiOperation(
		value = "nickname 중복 확인",
		notes = "회원가입 정보 입력시 nickname 중복 여부를 확인합니다."
	)
	@GetMapping("/nickname-check/{nickname}")
	public ResponseEntity<ApiResponse<CheckDuplicatedResponse>> checkNickname(@PathVariable String nickname) {
		return ResponseEntity.ok(ApiResponse.of(userService.checkNickname(nickname)));
	}

	@ApiOperation(
		value = "회원 탈퇴",
		notes = "가입된 계정을 탈퇴 처리합니다."
	)
	@DeleteMapping("/me")
	public ResponseEntity<ApiResponse<SuccessResponse>> deactivateUser(@AuthenticationPrincipal JwtAuthentication authentication) {
		userService.deactivateUser(authentication.username);

		return ResponseEntity.ok(ApiResponse.of(new SuccessResponse(true)));
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

	@ApiOperation(
		value = "사용자 정보 수정",
		notes = "사용자 정보를 수정합니다."
	)
	@PutMapping("/me")
	public ResponseEntity<ApiResponse<UserIdResponse>> changeInfoUser(
		@RequestBody UserChangeInfoRequest userChangeInfoRequest,
		@AuthenticationPrincipal JwtAuthentication authentication
	) {

		return ResponseEntity.ok(
			ApiResponse.of(
				userService.changeInfoUser(userChangeInfoRequest, authentication.username)));
	}

	@ApiOperation(
		value = "부주장 이상 권한을 가진 소속된 팀 리스트 조회",
		notes = "자신이 소속된 팀 중 부주장 또는 주장의 권한을 맡고 있는 팀 리스트를 조회합니다."
	)
	@GetMapping("/me/teams")
	public ResponseEntity<ApiResponse<AffiliatedTeamsResponse>> getMyTeams(@AuthenticationPrincipal JwtAuthentication authentication) {

		return ResponseEntity.ok(
			ApiResponse.of(
				userService.getMyTeams(authentication.username)));
	}

	@ApiOperation(
		value = "마이 페이지 조회",
		notes = "사용자의 마이 페이지를 조회합니다."
	)
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserInfoResponse>> getMyInfo(@AuthenticationPrincipal JwtAuthentication authentication) {

		return ResponseEntity.ok(
			ApiResponse.of(
				userService.getMyInfo(authentication.username)));
	}

	@ApiOperation(
		value = "사용자 페이지 조회",
		notes = "다른 사용자의 마이 페이지를 조회합니다."
	)
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo(@PathVariable Long userId) {

		return ResponseEntity.ok(
			ApiResponse.of(
				userService.getUserInfo(userId)));
	}

	@ApiOperation(
		value = "사용자의 경기 리스트 조회",
		notes = "사용자가 참여한 경기 리스트를 조회합니다."
	)
	@GetMapping("/{userId}/matches")
	public ResponseEntity<ApiResponse<UserMatchesResponse>> getUserMatches(@PathVariable Long userId) {

		return ResponseEntity.ok(
			ApiResponse.of(
				userService.getUserMatches(userId)));
	}

}
