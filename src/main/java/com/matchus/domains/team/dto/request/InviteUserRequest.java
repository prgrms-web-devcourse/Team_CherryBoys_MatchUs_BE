package com.matchus.domains.team.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class InviteUserRequest {

	@Email
	@NotBlank
	private String email;

	public InviteUserRequest() {
	}

	public InviteUserRequest(String email) {
		this.email = email;
	}
}
