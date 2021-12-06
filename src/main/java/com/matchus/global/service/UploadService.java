package com.matchus.global.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.InputStream;

public interface UploadService {

	void uploadFile(String fileName, InputStream inputStream, ObjectMetadata objectMetadata);

	String getFileUrl(String fileName);
}
