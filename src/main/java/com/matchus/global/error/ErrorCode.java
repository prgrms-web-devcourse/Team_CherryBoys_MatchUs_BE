package com.matchus.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// 500
	INTERNAL_SERVER_ERROR("정의되지 않은 에러가 발생했습니다.", 500),
	FILE_UPLOAD_ERROR("파일 변환 중 에러가 발생했습니다.", 500),

	// 400
	INVALID_INPUT_VALUE("올바른 입력 형식이 아닙니다.", 400),
	ENTITY_NOT_FOUND("엔티티를 찾을 수 없습니다.", 400),	
	AGEGROUP_NOT_FOUND("나이대가 존재하지 않습니다.", 400),
	INVALID_FILE_TYPE("잘못된 형식의 파일 입니다.", 400),
  
  	//401
  	UNAUTHORIZED_USER("권한이 없는 사용자입니다.", 401);
	;


	private final String message;
	private final int status;

	ErrorCode(final String message, final int status) {
		this.message = message;
		this.status = status;
	}
}
