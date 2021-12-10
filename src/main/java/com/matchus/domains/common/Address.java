package com.matchus.domains.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String region;

	@Column(nullable = false)
	private String groundName;

	public Address(String city, String region, String groundName) {
		this.city = city;
		this.region = region;
		this.groundName = groundName;
	}
}
