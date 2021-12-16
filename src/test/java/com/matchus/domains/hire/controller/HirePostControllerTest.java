package com.matchus.domains.hire.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.hire.dto.response.HirePostInfoResponse;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponse;
import com.matchus.domains.hire.dto.response.HirePostRetrieveByFilterResponse;
import com.matchus.domains.hire.service.HirePostService;
import com.matchus.domains.location.domain.City;
import com.matchus.domains.location.domain.Ground;
import com.matchus.domains.location.domain.Region;
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
				new HirePostListFilterResponse(
					30L,
					"윙백",
					"서울",
					"광진구",
					"아차산풋살장",
					LocalDate.now(),
					LocalTime.now(),
					LocalTime.now().plusHours(2),
					AgeGroup.TWENTIES.getAgeGroup(),
					"세부내용",
					1,
					1L,
					"팀로고1",
					"팀이름1",
					new BigDecimal("36.5")
				),
				new HirePostListFilterResponse(
					29L,
					"윙백",
					"서울",
					"광진구",
					"아차산풋살장",
					LocalDate.now(),
					LocalTime.now(),
					LocalTime.now().plusHours(2),
					AgeGroup.TWENTIES.getAgeGroup(),
					"세부내용",
					1,
					1L,
					"팀로고1",
					"팀이름1",
					new BigDecimal("36.5")
				),
				new HirePostListFilterResponse(
					28L,
					"윙백",
					"서울",
					"광진구",
					"아차산풋살장",
					LocalDate.now(),
					LocalTime.now(),
					LocalTime.now().plusHours(2),
					AgeGroup.TWENTIES.getAgeGroup(),
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

	@DisplayName("용병 구인 게시글 상세조회 테스트")
	@Test
	void getHirePostTest() throws Exception {
		// given
		Long postId = 1L;
		String position = "윙백";
		City city = new City(1L, "서울특별시");
		Region region = new Region(1L, city, "강남구");
		Ground ground = new Ground(1L, region, "대륭축구장");
		Period period = new Period(
			LocalDate.parse("2021-12-10"),
			LocalTime.of(12, 30),
			LocalTime.of(14, 30)
		);
		AgeGroup ageGroup = AgeGroup.TWENTIES;
		String detail = "세부내용";
		int hirePlayerNumber = 1;
		Long teamId = 1L;
		String teamName = "팀이름1";
		String teamLogo = "팀로고.jpg";
		BigDecimal teamMannerTemperature = new BigDecimal("36.5");
		long teamCaptainId = 1L;
		String teamCaptainName = "쭝";
		HirePostInfoResponse response = new HirePostInfoResponse(
			postId,
			city.getName(),
			region.getName(),
			ground.getName(),
			position,
			ageGroup.getAgeGroup(),
			hirePlayerNumber,
			detail,
			period.getDate(),
			period.getStartTime(),
			period.getEndTime(),
			teamId,
			teamName,
			teamLogo,
			teamMannerTemperature,
			teamCaptainId,
			teamCaptainName
		);
		given(hirePostService.getHirePost(postId)).willReturn(response);

		// when, then
		mockMvc
			.perform(
				get("/hires/{postId}", postId)
					.contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("UTF-8")
			)
			.andDo(print())
			.andExpectAll(
				status().isOk(),
				jsonPath("$.data").isNotEmpty()
			);
	}
}
