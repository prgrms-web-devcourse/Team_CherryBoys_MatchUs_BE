package com.matchus.domains.location.controller;

import com.matchus.domains.location.dto.response.LocationResult;
import com.matchus.domains.location.service.LocationService;
import com.matchus.global.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LocationController {

	private final LocationService locationService;

	@ApiOperation(
		value = "위치 정보 조회",
		notes = "시/도, 시/군/구, 구장 리스트를 조회합니다."
	)
	@GetMapping("/locations")
	public ResponseEntity<ApiResponse<LocationResult>> getLocation() {
		return ResponseEntity.ok(
			ApiResponse.of(locationService.getLocations())
		);
	}
}
