package com.matchus.global.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileUploadService {

	private static final String DIRECTORY = "logo/";

	private final UploadService s3UploadService;

	public String uploadImage(MultipartFile file) {
		String fileName = DIRECTORY + createFileName(file.getOriginalFilename());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(file.getContentType());
		objectMetadata.setContentLength(file.getSize());

		try (InputStream inputStream = file.getInputStream()) {
			s3UploadService.uploadFile(fileName, inputStream, objectMetadata);
		} catch (IOException e) {
			throw new IllegalArgumentException(
				MessageFormat.format("파일 변환 중 에러가 발생하였습니다. {0}", file.getOriginalFilename())
			);
		}

		return s3UploadService.getFileUrl(fileName);
	}

	private String createFileName(String originalFileName) {
		return UUID
			.randomUUID()
			.toString()
			.concat(getFileExtension(originalFileName));
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
				MessageFormat.format("잘못된 형식의 파일 {0} 입니다", fileName));
		}
	}
}
