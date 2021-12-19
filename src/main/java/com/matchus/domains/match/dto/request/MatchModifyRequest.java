package com.matchus.domains.match.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@RequiredArgsConstructor
public class MatchModifyRequest {

	@Min(value = 1, message = "도시 정보는 필수 입력 값입니다.")
	private final Long city;

	@Min(value = 1, message = "지역 정보는 필수 입력 값입니다.")
	private final Long region;

	@Min(value = 1, message = "구장 정보는 필수 입력 값입니다.")
	private final Long ground;

	@NotBlank(message = "주종목은 필수 입력 값입니다.")
	private final String sprotName;

	@Future(message = "현재보다 이른 날짜는 선택할 수 없습니다")
	@NotNull(message = "날짜 정보는 필수 입력 값입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private final LocalDate date;

	@NotNull(message = "시간 정보는 필수 입력 값입니다.")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime startTime;

	@NotNull(message = "시간 정보는 필수 입력 값입니다.")
	@DateTimeFormat(pattern = "HH:mm:ss")
	private final LocalTime endTime;

	@NotBlank(message = "연령대는 필수 입력 값입니다.")
	private final String ageGroup;

	@Min(value = 10, message = "참가비는 필수 입력 값입니다.")
	private final int cost;

	private final String detail;

}
