package com.matchus.domains.common;

import com.matchus.domains.common.exception.InvalidLocalTimeDataException;
import com.matchus.global.error.ErrorCode;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Period {

	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate date;

	@Column(nullable = false, columnDefinition = "TIME")
	private LocalTime startTime;

	@Column(nullable = false, columnDefinition = "TIME")
	private LocalTime endTime;

	public Period(LocalDate date, LocalTime startTime, LocalTime endTime) {
		validTime(startTime, endTime);
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	private void validTime(LocalTime startTime, LocalTime endTime) {
		if (startTime.isAfter(endTime)) {
			throw new InvalidLocalTimeDataException(ErrorCode.INVALID_TIME_DATA);
		}
	}
}
