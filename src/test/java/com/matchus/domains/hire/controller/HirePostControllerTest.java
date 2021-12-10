package com.matchus.domains.hire.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponseDto;
import com.matchus.domains.hire.dto.response.HirePostRetrieveByFilterResponse;
import com.matchus.domains.hire.service.HirePostService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class HirePostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HirePostService hirePostService;

	@Autowired
	private WebApplicationContext ctx;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(ctx)
			.addFilters(new CharacterEncodingFilter(
				"UTF-8",
				true
			))
			.alwaysDo(print())
			.build();
	}

	@DisplayName("용병 구인 게시글 리스트 필터 조회 테스트")
	@Test
	void retrieveHirePosts() throws Exception {
		// given
		HirePostRetrieveByFilterResponse response = new HirePostRetrieveByFilterResponse(
			List.of(
				new HirePostListFilterResponseDto(
					30L,
					"윙백",
					"서울",
					"광진구",
					"아차산풋살장",
					LocalDate.now(),
					LocalTime.now(),
					LocalTime.now().plusHours(2),
					AgeGroup.TWENTIES,
					"세부내용",
					1,
					1L,
					"팀로고1",
					"팀이름1",
					new BigDecimal("36.5")
				),
				new HirePostListFilterResponseDto(
					29L,
					"윙백",
					"서울",
					"광진구",
					"아차산풋살장",
					LocalDate.now(),
					LocalTime.now(),
					LocalTime.now().plusHours(2),
					AgeGroup.TWENTIES,
					"세부내용",
					1,
					1L,
					"팀로고1",
					"팀이름1",
					new BigDecimal("36.5")
				),
				new HirePostListFilterResponseDto(
					28L,
					"윙백",
					"서울",
					"광진구",
					"아차산풋살장",
					LocalDate.now(),
					LocalTime.now(),
					LocalTime.now().plusHours(2),
					AgeGroup.TWENTIES,
					"세부내용",
					1,
					1L,
					"팀로고1",
					"팀이름1",
					new BigDecimal("36.5")
				)
			)
		);
		given(hirePostService.retrieveHirePostsNoOffsetByFilter(any(), any())).willReturn(response);

		// when, then
		mockMvc
			.perform(
				get("/hires")
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8")
					.with(user("jake").roles("USER")))
			.andDo(print())
			.andExpectAll(
				status().isOk(),
				jsonPath("$.data.hirePosts").isArray()
			);
	}
}