package com.matchus.domains.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private final String email;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$",
		message = "패스워드는 특수문자, 대문자, 소문자를 포함해야 합니다.")
	@Size(min = 10, max = 16, message = "패스워드는 10자 이상 16자 이하이어야 합니다.")
	private final String password;

	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
