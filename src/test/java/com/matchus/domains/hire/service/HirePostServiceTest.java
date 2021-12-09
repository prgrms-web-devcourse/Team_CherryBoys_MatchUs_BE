package com.matchus.domains.hire.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.service.SportsService;
import com.matchus.domains.hire.dto.request.HirePostRetrieveFilterRequest;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponseDto;
import com.matchus.domains.hire.dto.response.HirePostRetrieveByFilterResponse;
import com.matchus.domains.hire.repository.HirePostRepository;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.global.utils.PageRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HirePostServiceTest {

	@InjectMocks
	private HirePostService hirePostService;

	@Mock
	private HirePostRepository hirePostRepository;

	@Mock
	private SportsService sportsService;

	@DisplayName("용병 구인 게시글 필터 조회 성공 테스트")
	@Test
	void retrieveHirePostsNoOffsetByFilter() {
		// given
		Sports sports = new Sports(1L, "축구");
		given(sportsService.findByName(any())).willReturn(sports);

		List<HirePostListFilterResponseDto> hirePosts = List.of(
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
		);

		PageRequest pageRequest = new PageRequest(null, 30);
		given(hirePostRepository.findAllNoOffsetByFilter(
			"윙백",
			1L,
			null,
			null,
			null,
			null,
			null,
			pageRequest
		)).willReturn(hirePosts);

		HirePostRetrieveFilterRequest filterRequest = new HirePostRetrieveFilterRequest(
			"윙백",
			"축구",
			null,
			null,
			null,
			null,
			null
		);

		// when
		HirePostRetrieveByFilterResponse hirePostRetrieveByFilterResponse =
			hirePostService.retrieveHirePostsNoOffsetByFilter(
				filterRequest,
				pageRequest
			);

		// then
		assertThat(hirePostRetrieveByFilterResponse.getHirePosts()).hasSize(3);
	}
}
