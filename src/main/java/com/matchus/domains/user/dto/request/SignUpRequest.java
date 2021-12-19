package com.matchus.domains.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotBlank(message = "이름은 필수 입력 값입니다.")
	@Size(max = 8)
	private final String name;

	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private final String email;

	@Size(max = 8, message = "닉네임은 8자까지 만들 수 있습니다.")
	@NotBlank(message = "닉네임 필수 입력 값입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎ]*$", message = "닉네임에는 특수문자가 들어갈 수 없습니다.")
	private final String nickname;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$",
		message = "패스워드는 특수문자, 대문자, 소문자를 포함해야 합니다.")
	@Size(min = 10, max = 16, message = "패스워드는 10자 이상 16자 이하이어야 합니다.")
	private final String password;

	@NotBlank(message = "연령대는 필수 입력 값입니다.")
	private final String ageGroup;

	@NotBlank(message = "주종목은 필수 입력 값입니다.")
	private final String sports;

	@NotBlank(message = "성별은 필수 입력 값입니다.")
	private final String gender;

	@Builder
	public SignUpRequest(
		String name,
		String email,
		String nickname,
		String password,
		String ageGroup,
		String sports,
		String gender
	) {
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.ageGroup = ageGroup;
		this.sports = sports;
		this.gender = gender;
	}

}
