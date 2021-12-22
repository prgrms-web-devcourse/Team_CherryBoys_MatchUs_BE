package com.matchus.domains.tag.controller;

import com.matchus.domains.tag.dto.response.TagResponse;
import com.matchus.domains.tag.service.TagService;
import com.matchus.global.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "태그")
@RequiredArgsConstructor
@RestController
public class TagController {

	private final TagService tagService;

	@ApiOperation(
		value = "태그 리스트 조회",
		notes = "태그 종류를 모두 조회합니다."
	)
	@GetMapping("/tags")
	public ResponseEntity<ApiResponse<TagResponse>> getTags() {
		return ResponseEntity.ok(
			ApiResponse.of(tagService.getTags())
		);
	}
}
