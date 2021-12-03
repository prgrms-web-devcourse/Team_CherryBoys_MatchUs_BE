package com.matchus.domains.common;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Period {

	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate date;

	@Column(nullable = false, columnDefinition = "TIME")
	private LocalTime startTime;

	@Column(nullable = false, columnDefinition = "TIME")
	private LocalTime endTime;
}
