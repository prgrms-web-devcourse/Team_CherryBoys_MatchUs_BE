package com.matchus.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INTERNAL_SERVER_ERROR("정의되지 않은 에러가 발생했습니다.", 500),
	INVALID_INPUT_VALUE("올바른 입력 형식이 아닙니다.", 400),
	ENTITY_NOT_FOUND("엔티티를 찾을 수 없습니다.", 400),
	UNAUTHORIZED_USER("권한이 없는 사용자입니다.", 401);

	private final String message;
	private final int status;

	ErrorCode(final String message, final int status) {
		this.message = message;
		this.status = status;
	}
}
