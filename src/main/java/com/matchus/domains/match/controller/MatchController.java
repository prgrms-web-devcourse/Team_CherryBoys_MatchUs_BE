package com.matchus.domains.match.controller;

import com.matchus.domains.match.dto.request.MatchCreateRequest;
import com.matchus.domains.match.dto.request.MatchModifyRequest;
import com.matchus.domains.match.dto.request.MatchRetrieveFilterRequest;
import com.matchus.domains.match.dto.request.MatchTeamInfoRequest;
import com.matchus.domains.match.dto.response.MatchIdResponse;
import com.matchus.domains.match.dto.response.MatchInfoResponse;
import com.matchus.domains.match.dto.response.MatchRetrieveByFilterResponse;
import com.matchus.domains.match.dto.response.MatchWaitingListResponse;
import com.matchus.domains.match.service.MatchService;
import com.matchus.global.response.ApiResponse;
import com.matchus.global.utils.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "매치")
@RestController
public class MatchController {

	private final MatchService matchService;

	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	@ApiOperation(
		value = "매치 글 작성",
		notes = "매칭 글을 작성합니다."
	)
	@PostMapping("/matches")
	public ResponseEntity<ApiResponse<MatchIdResponse>> createMatchPost(@RequestBody MatchCreateRequest request) {
		return ResponseEntity.ok(ApiResponse.of(matchService.createMatchPost(request)));
	}

	@ApiOperation(
		value = "매치 글 상세 조회",
		notes = "매칭 글을 매칭 대기 & 매칭 완료 상황에 따라 상세 조회합니다."
	)
	@GetMapping("/matches/{matchId}")
	public ResponseEntity<ApiResponse<MatchInfoResponse>> getMatchInfo(@PathVariable Long matchId) {
		return ResponseEntity.ok(ApiResponse.of(matchService.getMatchInfo(matchId)));
	}

	@ApiOperation(
		value = "매치 정보 수정",
		notes = "매칭 정보를 수정합니다."
	)
	@PutMapping("/matches/{matchId}")
	public ResponseEntity<ApiResponse<MatchIdResponse>> changeMatchInfo(
		@PathVariable Long matchId,
		@RequestBody MatchModifyRequest request
	) {
		return ResponseEntity.ok(ApiResponse.of(matchService.matchChangeInfo(request, matchId)));
	}

	@ApiOperation(
		value = "매치 게시글 리스트 필터 조회",
		notes = "매치 게시글 리스트를 조회합니다. 필터를 설정하여 조회할 수 있습니다."
	)
	@GetMapping("/matches")
	public ResponseEntity<ApiResponse<MatchRetrieveByFilterResponse>> retrieveMatches(
		@ModelAttribute MatchRetrieveFilterRequest filterRequest,
		PageRequest pageRequest
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				matchService.retrieveMatchNoOffsetByFilter(filterRequest, pageRequest)
			)
		);
	}

	@ApiOperation(
		value = "매치 신청 대기팀 리스트 조회",
		notes = "매치 신청 대기 팀&팀원 리스트를 조회합니다."
	)
	@GetMapping("/matches/{matchId}/waitings")
	public ResponseEntity<ApiResponse<MatchWaitingListResponse>> getMatchWaitingList(
		@PathVariable long matchId
	) {
		return ResponseEntity.ok(ApiResponse.of(matchService.getMatchWaitingList(matchId)));
	}

	@ApiOperation(
		value = "매치 신청",
		notes = "매치 게시글에 대해 매치를 신청한다."
	)
	@PostMapping("/matchs/{matchId}/waitings")
	public ResponseEntity<ApiResponse<MatchIdResponse>> applyMatch(
		@PathVariable Long matchId,
		@RequestBody MatchTeamInfoRequest request
	) {

		return ResponseEntity.ok(ApiResponse.of(matchService.applyMatch(matchId, request)));
	}

	@ApiOperation(
		value = "매치 신청 수락",
		notes = "매치에 대한 특정 팀의 매칭 신청을 수락한다."
	)
	@PostMapping("/match-waitings/{teamWaitingId}")
	public ResponseEntity<ApiResponse<MatchIdResponse>> acceptMatch(@PathVariable Long teamWaitingId) {

		return ResponseEntity.ok(ApiResponse.of(matchService.acceptMatch(teamWaitingId)));
	}

	@ApiOperation(
		value = "매치 팀원 명단 수정",
		notes = "매치에 등록한 팀원 명단을 수정한다."
	)
	@PutMapping("/matches/{matchId}/members")
	public ResponseEntity<ApiResponse<MatchIdResponse>> acceptMatch(
		@PathVariable Long matchId,
		@RequestBody MatchTeamInfoRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.of(matchService.changeMatchMembersInfo(request, matchId)));
	}
}
