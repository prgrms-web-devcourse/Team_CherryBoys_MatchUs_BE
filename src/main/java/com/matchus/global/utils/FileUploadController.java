package com.matchus.global.utils;

import com.matchus.global.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

	private final FileUploadService fileUploadService;

	@PostMapping("/upload")
	public String uploadImage(@RequestPart MultipartFile file) {
		return fileUploadService.uploadImage(file);
	}
}
