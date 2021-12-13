package com.matchus.domains.hire.controller;

import com.matchus.domains.hire.dto.request.HireApplyRequest;
import com.matchus.domains.hire.dto.response.HireApplyResponse;
import com.matchus.domains.hire.service.HireApplicationService;
import com.matchus.global.jwt.JwtAuthentication;
import com.matchus.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hire-applications")
public class HireApplicationController {

	private final HireApplicationService hireApplicationService;

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
}
