package com.matchus.domains.hire.controller;

import com.matchus.domains.hire.dto.request.HirePostRetrieveFilterRequest;
import com.matchus.domains.hire.dto.response.HirePostInfoResponse;
import com.matchus.domains.hire.dto.response.HirePostRetrieveByFilterResponse;
import com.matchus.domains.hire.service.HirePostService;
import com.matchus.global.response.ApiResponse;
import com.matchus.global.utils.PageRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hires")
public class HirePostController {

	private final HirePostService hirePostService;

	@ApiOperation(
		value = "용병 구인 게시글 리스트 필터 조회",
		notes = "용병 구인 게시글 리스트를 조회합니다. 필터를 설정하여 조회할 수 있습니다."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<HirePostRetrieveByFilterResponse>> retrieveHirePosts(
		@ModelAttribute HirePostRetrieveFilterRequest filterRequest,
		PageRequest pageRequest
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				hirePostService.retrieveHirePostsNoOffsetByFilter(filterRequest, pageRequest)
			)
		);
	}

	@ApiOperation(
		value = "용병 구인 게시글 상세 조회",
		notes = "용병 구인 게시글(단건)을 상세 조회합니다."
	)
	@GetMapping("/{postId}")
	public ResponseEntity<ApiResponse<HirePostInfoResponse>> getHirePost(
		@PathVariable Long postId
	) {
		return ResponseEntity.ok(
			ApiResponse.of(
				hirePostService.getHirePost(postId)
			)
		);
	}
}
