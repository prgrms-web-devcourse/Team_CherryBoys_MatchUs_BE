package com.matchus.domains.team.controller;

import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.dto.request.TeamModifyRequest;
import com.matchus.domains.team.dto.response.TeamModifyResponse;
import com.matchus.domains.team.service.TeamService;
import com.matchus.global.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Void> createTeam(@ModelAttribute TeamCreateRequest request) {
		teamService.createTeam(request);
		return ResponseEntity.ok().build();
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
}
