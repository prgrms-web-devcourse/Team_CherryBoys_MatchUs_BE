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
	HIRE_APPLICATION_NOT_FOUND("유효하지 않은 용병 신청 내역입니다.", 400),
	HIRE_POST_NOT_FOUND("해당 용병 게시글이 존재하지 않습니다.", 400),
	TEAM_NOT_FOUND("존재하지 않는 팀 입니다.", 400),
	USER_NOT_FOUND("해당 유저가 존재하지 않습니다.", 400),
	TEAM_USER_NOT_FOUND("팀-팀원이 존재하지 않습니다.", 400),
	TAG_NOT_FOUND("존재하지 않는 태그입니다.", 400),
	TEAM_WAITING_NOT_FOUND("매칭 팀이 존재하지 않습니다.", 400),
	AGEGROUP_NOT_FOUND("나이대가 존재하지 않습니다.", 400),
	GENDER_NOT_FOUND("성별이 존재하지 않습니다.", 400),
	GRADE_NOT_FOUND("등급이 존재하지 않습니다.", 400),
	TEAM_TYPE_NOT_FOUND("팀 타입이 존재하지 않습니다.", 400),
	TEAM_INVITATION_NOT_FOUND("팀원 초대 요청이 존재하지 않습니다.", 400),
	INVALID_FILE_TYPE("잘못된 형식의 파일 입니다.", 400),
	TEAM_INVITATION_ALREADY_EXISTS("기존 초대 요청이 존재합니다.", 400),
	UNAUTHORIZED_TEAM_USER("권한이 없는 팀원입니다.", 400),
	TEAM_USER_ALREADY_EXISTS("기존 팀원이 존재합니다.", 400),
	APPLY_TEAM_ALREADY_EXISTS("기존 상대 팀이 존재합니다.", 400),

	//401
	UNAUTHORIZED_USER("권한이 없는 사용자입니다.", 401),
	;

	private final String message;
	private final int status;

	ErrorCode(final String message, final int status) {
		this.message = message;
		this.status = status;
	}
}
