package com.matchus.domains.hire.controller;

import com.matchus.domains.hire.dto.request.HireApplyRequest;
import com.matchus.domains.hire.dto.response.HireApplyResponse;
import com.matchus.domains.hire.service.HireApplicationService;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.response.ApiResponse;
import com.matchus.global.response.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hire-applications")
public class HireApplicationController {

	private final HireApplicationService hireApplicationService;

	@ApiOperation(
		value = "용병 신청",
		notes = "용병 구인 게시글에서 용병 신청 버튼을 통해 용병 신청을 할 수 있습니다."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<HireApplyResponse>> applyHire(
		@RequestBody HireApplyRequest request,
		@AuthenticationPrincipal JwtAuthentication authentication
	) {
		String userEmail = authentication.username;
		return ResponseEntity.ok(
			ApiResponse.of(hireApplicationService.applyHire(request, userEmail))
		);
	}

	@ApiOperation(
		value = "용병 신청 취소",
		notes = "용병 신청을 취소합니다."
	)
	@DeleteMapping("/{applicationsId}")
	public ResponseEntity<ApiResponse<SuccessResponse>> cancelApplication(
		@PathVariable Long applicationsId
	) {
		return ResponseEntity.ok(
			ApiResponse.of(hireApplicationService.cancelApplication(applicationsId))
		);
	}
}
