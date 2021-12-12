package com.matchus.domains.match.controller;

import com.matchus.domains.match.dto.MatchCreateRequest;
import com.matchus.domains.match.dto.MatchCreateResponse;
import com.matchus.domains.match.dto.MatchInfoResponse;
import com.matchus.domains.match.service.MatchService;
import com.matchus.global.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class MatchController {

	private final MatchService matchService;

	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	@ApiOperation(
		value = "매치 글 작성",
		notes = "매칭 글을 작성합니다."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<MatchCreateResponse>> createMatchPost(@RequestBody MatchCreateRequest request) {
		return ResponseEntity.ok(ApiResponse.of(matchService.createMatchPost(request)));
	}

	@ApiOperation(
		value = "매치 글 상세 조회",
		notes = "매칭 글을 매칭 대기 & 매칭 완료 상황에 따라 상세 조회합니다."
	)
	@GetMapping("/{matchId}")
	public ResponseEntity<ApiResponse<MatchInfoResponse>> getMatchInfo(@PathVariable Long matchId) {
		return ResponseEntity.ok(ApiResponse.of(matchService.getMatchInfo(matchId)));
	}

}
