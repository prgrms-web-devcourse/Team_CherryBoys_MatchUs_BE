package com.matchus.domains.team.controller;

import com.matchus.domains.team.dto.response.TeamIdResponse;
import com.matchus.domains.team.service.TeamUserJoining;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "팀")
@RequiredArgsConstructor
@RestController
public class TeamUserController {

	private final TeamUserJoining teamUserJoining;

	@ApiOperation(
		value = "팀 탈퇴",
		notes = "팀원으로 속해있는 팀에서 탈퇴합니다."
	)
	@DeleteMapping("/teams/{teamId}/me")
	public ResponseEntity<ApiResponse<TeamIdResponse>> leaveTeam(
		@PathVariable Long teamId,
		@AuthenticationPrincipal JwtAuthentication authentication
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamUserJoining.leaveTeam(teamId, authentication.username))
		);
	}
}
