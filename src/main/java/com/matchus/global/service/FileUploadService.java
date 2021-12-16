package com.matchus.global.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.error.exception.FileUploadException;
import com.matchus.global.error.exception.InvalidFileTypeException;
import java.io.IOException;
import java.io.InputStream;
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
		if (file.isEmpty()) {
			return null;
		}

		String fileName = DIRECTORY + createFileName(file.getOriginalFilename());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(file.getContentType());
		objectMetadata.setContentLength(file.getSize());

		try (InputStream inputStream = file.getInputStream()) {
			s3UploadService.uploadFile(fileName, inputStream, objectMetadata);
		} catch (IOException e) {
			throw new FileUploadException(ErrorCode.FILE_UPLOAD_ERROR);
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
			throw new InvalidFileTypeException(ErrorCode.INVALID_FILE_TYPE);
		}
	}
}
