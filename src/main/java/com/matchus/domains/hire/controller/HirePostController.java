package com.matchus.domains.hire.controller;

import com.matchus.domains.hire.dto.request.ApplicationsAcceptRequest;
import com.matchus.domains.hire.dto.request.HirePostModifyRequest;
import com.matchus.domains.hire.dto.request.HirePostRetrieveFilterRequest;
import com.matchus.domains.hire.dto.request.HirePostWriteRequest;
import com.matchus.domains.hire.dto.response.ApplicationsAcceptResponse;
import com.matchus.domains.hire.dto.response.HireApplicationsResponse;
import com.matchus.domains.hire.dto.response.HirePostInfoResponse;
import com.matchus.domains.hire.dto.response.HirePostModifyResponse;
import com.matchus.domains.hire.dto.response.HirePostRetrieveByFilterResponse;
import com.matchus.domains.hire.dto.response.HirePostWriteResponse;
import com.matchus.domains.hire.service.HirePostService;
import com.matchus.global.response.ApiResponse;
import com.matchus.global.utils.PageRequest;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@ApiOperation(
		value = "용병 구인 게시글 작성",
		notes = "용병 구인 게시글을 작성합니다."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<HirePostWriteResponse>> writeHirePost(
		@Valid @RequestBody HirePostWriteRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.of(hirePostService.writeHirePost(request))
		);
	}

	@ApiOperation(
		value = "용병 구인 게시글 수정",
		notes = "용병 구인 게시글을 수정합니다."
	)
	@PutMapping("/{postId}")
	public ResponseEntity<ApiResponse<HirePostModifyResponse>> modifyHirePost(
		@PathVariable Long postId,
		@Valid @RequestBody HirePostModifyRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.of(hirePostService.modifyHirePost(postId, request))
		);
	}

	@ApiOperation(
		value = "용병 구인 게시글 삭제",
		notes = "용병 구인 게시글을 삭제합니다."
	)
	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> removeHirePost(@PathVariable Long postId) {
		hirePostService.removeHirePost(postId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(
		value = "용병 신청자 리스트 조회",
		notes = "용병 구인 게시글에 용병 신청한 용병 신청자 리스트를 조회합니다."
	)
	@GetMapping("/{postId}/applications")
	public ResponseEntity<ApiResponse<HireApplicationsResponse>> getHireApplications(@PathVariable Long postId) {
		return ResponseEntity.ok(
			ApiResponse.of(hirePostService.getHireApplications(postId))
		);
	}

	@ApiOperation(
		value = "용병 수락",
		notes = "용병 구인 게시글의 용병 신청을 수락하여 용병 팀원으로 등록합니다."
	)
	@PostMapping("/{postId}/applications")
	public ResponseEntity<ApiResponse<ApplicationsAcceptResponse>> acceptHireApplications(
		@PathVariable Long postId,
		@RequestBody ApplicationsAcceptRequest request
	) {
		return ResponseEntity.ok(
			ApiResponse.of(hirePostService.acceptHireApplications(postId, request))
		);
	}
}
