package com.matchus.domains.hire.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.matchus.domains.hire.service.HirePostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles("test")
@ComponentScan(
	basePackages = {
		"com.matchus.global.config.WebSecurityConfig",
		"com.matchus.global.jwt"
	}
)
@WebMvcTest(HirePostController.class)
class HirePostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HirePostService hirePostService;

	@DisplayName("용병 구인 게시글 리스트 필터 조회 테스트")
	@Test
	void retrieveHirePosts() throws Exception {
		// given

		// when, then
		mockMvc
			.perform(
				get("/hires")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpectAll(
				status().isOk(),
				jsonPath("$.data.hirePosts").isArray()
			);
	}
}
