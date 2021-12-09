package com.matchus.global.utils;

import lombok.Getter;

@Getter
public class PageRequest {

	private Long lastId;
	private Integer size;

	public PageRequest() {
	}

	public PageRequest(Long lastId, Integer size) {
		this.lastId = lastId;
		this.size = size;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	public void setSize(Integer size) {
		final int defaultSize = 20;
		this.size = (size == null ? defaultSize : size);
	}
}
