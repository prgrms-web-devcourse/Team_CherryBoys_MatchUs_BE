package com.matchus.global.error.exception;

import com.matchus.global.error.ErrorCode;

public class FileUploadException extends BusinessException {

	public FileUploadException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	public FileUploadException(ErrorCode errorCode) {
		super(errorCode);
	}
}
