package com.matchus.global.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {

	private Long lastId;
	private int size;

	public PageRequest(Long lastId, int size) {
		this.lastId = lastId;
		this.size = size;
	}
}
