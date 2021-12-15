package com.matchus.domains.team.controller;

import com.matchus.domains.team.service.TeamInvitationService;
import com.matchus.global.response.ApiResponse;
import com.matchus.global.response.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invitations")
public class TeamInvitationController {

	private final TeamInvitationService teamInvitationService;

	public TeamInvitationController(TeamInvitationService teamInvitationService) {
		this.teamInvitationService = teamInvitationService;
	}

	@ApiOperation(
		value = "팀 초대 요청 수락",
		notes = "팀 초대 요청을 수락합니다."
	)
	@PostMapping("/{invitationId}")
	public ResponseEntity<ApiResponse<SuccessResponse>> acceptTeamInvitation(@PathVariable Long invitationId) {

		return ResponseEntity.ok(
			ApiResponse.of(teamInvitationService.acceptTeamInvitation(invitationId)));
	}

}
