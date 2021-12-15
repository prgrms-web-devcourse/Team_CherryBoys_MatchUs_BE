package com.matchus.domains.team.controller;

import com.matchus.domains.team.dto.request.ChangeGradesRequest;
import com.matchus.domains.team.dto.request.InviteUserRequest;
import com.matchus.domains.team.dto.request.RemoveMembersRequest;
import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.dto.request.TeamModifyRequest;
import com.matchus.domains.team.dto.response.TeamCreateResponse;
import com.matchus.domains.team.dto.response.TeamIdResponse;
import com.matchus.domains.team.dto.response.TeamInfoResponse;
import com.matchus.domains.team.dto.response.TeamMatchesResponse;
import com.matchus.domains.team.dto.response.TeamMembersResponse;
import com.matchus.domains.team.dto.response.TeamModifyResponse;
import com.matchus.domains.team.service.TeamService;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.response.ApiResponse;
import com.matchus.global.response.CheckDuplicatedResponse;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		value = "팀 정보 조회",
		notes = "팀 정보를 조회합니다."
	)
	@GetMapping("/{teamId}")
	public ResponseEntity<ApiResponse<TeamInfoResponse>> getTeamInfo(
		@PathVariable Long teamId
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.getTeamInfo(teamId))
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

	@ApiOperation(
		value = "팀 용병 조회",
		notes = "팀의 용병 리스트를 조회힙니다."
	)
	@GetMapping("/{teamId}/hired-members")
	public ResponseEntity<ApiResponse<TeamMembersResponse>> getTeamHiredMembers(
		@PathVariable Long teamId
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.getTeamHiredMembers(teamId))
		);
	}

	@ApiOperation(
		value = "전체 팀원 조회",
		notes = "팀에 소속되어있는 용병을 포함한 전체 팀원 리스트를 조회합니다."
	)
	@GetMapping("/{teamId}/total-members")
	public ResponseEntity<ApiResponse<TeamMembersResponse>> getTotalTeamMembers(
		@PathVariable Long teamId
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.getTotalTeamMembers(teamId))
		);
	}

	@ApiOperation(
		value = "팀 매칭 리스트 조회",
		notes = "팀이 참여한 매치 리스트를 조회합니다."
	)
	@GetMapping("/{teamId}/matches")
	public ResponseEntity<ApiResponse<TeamMatchesResponse>> getTeamMatches(
		@PathVariable Long teamId
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.getTeamMatches(teamId))
		);
	}

	@ApiOperation(
		value = "팀원 권한 수정",
		notes = "복수의 팀원들의 권한을 수정합니다."
	)
	@PutMapping("/{teamId}/members")
	public ResponseEntity<ApiResponse<TeamIdResponse>> changeMembersGrade(
		@PathVariable Long teamId,
		@RequestBody ChangeGradesRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.changeMembersGrade(teamId, request))
		);
	}

	@ApiOperation(
		value = "팀원 방출",
		notes = "팀에 속해있는 팀원/용병을 방출합니다."
	)
	@DeleteMapping("/{teamId}/members")
	public ResponseEntity<ApiResponse<TeamIdResponse>> removeMembers(
		@PathVariable Long teamId,
		@RequestBody RemoveMembersRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.removeMembers(teamId, request))
		);
	}

	@ApiOperation(
		value = "팀원 초대",
		notes = "특정 유저에게 팀으로의 초대 요청을 보냅니다."
	)
	@PostMapping("/{teamId}/members")
	public ResponseEntity<ApiResponse<TeamIdResponse>> inviteUser(
		@PathVariable Long teamId,
		@Valid @RequestBody InviteUserRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.of(teamService.inviteUser(teamId, request))
		);
	}

	@ApiOperation(
		value = "팀명 중복 확인",
		notes = "팀명 중복 확인합니다."
	)
	@GetMapping("/name-check")
	public ResponseEntity<ApiResponse<CheckDuplicatedResponse>> checkTeamName(
		@RequestParam String teamName
	) {
		return ResponseEntity.ok(ApiResponse.of(teamService.checkTeamName(teamName)));
	}
}
