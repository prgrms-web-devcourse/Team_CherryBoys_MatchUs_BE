package com.matchus.domains.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String region;

	@Column(nullable = false)
	private String groundName;
}
