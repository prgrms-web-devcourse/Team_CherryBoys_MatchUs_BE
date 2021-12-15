package com.matchus.domains.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.matchus.BaseIntegrationTest;
import com.matchus.domains.user.service.UserService;
import com.matchus.global.response.CheckDuplicatedResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;


class UserControllerTest extends BaseIntegrationTest {

	@MockBean
	private UserService userService;

	@Test
	@DisplayName("이메일 중복 여부 테스트")
	void checkEmailTest() throws Exception {

		String email = "sun77@gmail.com";

		CheckDuplicatedResponse checkDuplicatedResponse = new CheckDuplicatedResponse(true);

		given(userService.checkEmail(email)).willReturn(checkDuplicatedResponse);

		mockMvc
			.perform(
				get("/users/email-check/{email}", email)
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8"))
			.andDo(print())
			.andExpect(status().isOk());

	}

	@Test
	@DisplayName("닉네임중복 여부 테스트")
	void checkNicknameTest() throws Exception {

		String nickName = "삐약삐약";

		CheckDuplicatedResponse checkDuplicatedResponse = new CheckDuplicatedResponse(false);

		given(userService.checkNickname(nickName)).willReturn(checkDuplicatedResponse);

		mockMvc
			.perform(
				get("/users/nickname-check/{nickname}", nickName)
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8"))
			.andDo(print())
			.andExpect(status().isOk());

	}

}
