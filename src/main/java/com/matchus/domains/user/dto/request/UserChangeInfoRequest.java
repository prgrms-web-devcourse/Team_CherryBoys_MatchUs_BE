package com.matchus.domains.user.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserChangeInfoRequest {

	@Size(max = 8, message = "닉네임은 8자까지 만들 수 있습니다")
	@NotBlank(message = "닉네임 필수 입력 값입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎ]*$", message = "닉네임에는 특수문자가 들어갈 수 없습니다.")
	private String nickname;

	private String bio;

	@NotBlank(message = "성별은 필수 입력 값입니다.")
	private String ageGroup;

	@NotBlank(message = "주종목은 필수 입력 값입니다.")
	private String sportName;

	public UserChangeInfoRequest() {
	}

	public UserChangeInfoRequest(
		String nickname,
		String bio,
		String ageGroup,
		String sportName
	) {
		this.nickname = nickname;
		this.bio = bio;
		this.ageGroup = ageGroup;
		this.sportName = sportName;
	}

}
