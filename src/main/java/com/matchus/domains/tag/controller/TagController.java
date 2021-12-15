package com.matchus.domains.tag.controller;

import com.matchus.domains.tag.dto.response.TagResponse;
import com.matchus.domains.tag.service.TagService;
import com.matchus.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TagController {

	private final TagService tagService;

	@GetMapping("/tags")
	public ResponseEntity<ApiResponse<TagResponse>> getTags() {
		return ResponseEntity.ok(
			ApiResponse.of(tagService.getTags())
		);
	}
}
