package com.matchus.global.utils;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@ApiOperation("API 서버 Health check를 위한 API")
	@GetMapping("/health-check")
	public ResponseEntity<Void> test() {
		return ResponseEntity.ok(null);
	}
}
