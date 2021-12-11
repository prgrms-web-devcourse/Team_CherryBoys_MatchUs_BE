package com.matchus.domains.match.controller;

import com.matchus.domains.match.dto.MatchCreateRequest;
import com.matchus.domains.match.dto.MatchCreateResponse;
import com.matchus.domains.match.service.MatchService;
import com.matchus.global.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
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

}
