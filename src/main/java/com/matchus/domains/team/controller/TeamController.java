package com.matchus.domains.team.controller;

import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.dto.request.TeamModifyRequest;
import com.matchus.domains.team.dto.response.TeamCreateResponse;
import com.matchus.domains.team.dto.response.TeamMembersResponse;
import com.matchus.domains.team.dto.response.TeamModifyResponse;
import com.matchus.domains.team.service.TeamService;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

	private final TeamService teamService;

	@ApiOperation(
		value = "팀 생성",
		notes = "새로운 팀을 생성합니다."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<TeamCreateResponse>> createTeam(
		@ModelAttribute TeamCreateRequest request,
		@AuthenticationPrincipal JwtAuthentication authentication
	) {
		String userEmail = authentication.username;
		return ResponseEntity.ok(
			ApiResponse.of(teamService.createTeam(request, userEmail))
		);
	}

	@ApiOperation(
		value = "팀 정보 수정",
		notes = "팀 정보-로고, 팀소개, 연령대를 수정합니다."
	)
	@PutMapping("/{teamId}")
	public ResponseEntity<ApiResponse<TeamModifyResponse>> modifyTeam(
		@PathVariable Long teamId,
		@ModelAttribute TeamModifyRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.modifyTeam(teamId, request))
		);
	}

	@ApiOperation(
		value = "팀원 조회",
		notes = "팀에 소속되어 있는 팀원 리스트를 조회힙니다."
	)
	@GetMapping("/{teamId}/members")
	public ResponseEntity<ApiResponse<TeamMembersResponse>> getTeamMembers(
		@PathVariable Long teamId
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.getTeamMembers(teamId))
		);
	}
}
