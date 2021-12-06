package com.matchus.domains.team.controller;

import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.service.TeamService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
}
