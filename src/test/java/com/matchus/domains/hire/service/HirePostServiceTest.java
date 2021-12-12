package com.matchus.domains.hire.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.hire.converter.HirePostConverter;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.request.HirePostRetrieveFilterRequest;
import com.matchus.domains.hire.dto.response.HirePostInfoResponse;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponseDto;
import com.matchus.domains.hire.dto.response.HirePostRetrieveByFilterResponse;
import com.matchus.domains.hire.repository.HirePostRepository;
import com.matchus.domains.location.domain.City;
import com.matchus.domains.location.domain.Ground;
import com.matchus.domains.location.domain.Region;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.domain.Team;
import com.matchus.global.utils.PageRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
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

	@Mock
	private HirePostConverter hirePostConverter;

	@DisplayName("용병 구인 게시글 필터 조회 성공 테스트")
	@Test
	void retrieveHirePostsNoOffsetByFilter() {
		// given
		given(sportsService.getSportsOrNull(any())).willReturn(null);

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
			null,
			null,
			null,
			null,
			null,
			null,
			pageRequest
		)).willReturn(hirePosts);

		HirePostRetrieveFilterRequest filterRequest = new HirePostRetrieveFilterRequest(
			"윙백",
			null,
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

	@DisplayName("용병 구인 게시글 상세조회 성공 테스트")
	@Test
	void getHirePostTest() {
		// given
		long postId = 1L;
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
		HirePost hirePost = HirePost
			.builder()
			.id(postId)
			.position(position)
			.city(city)
			.region(region)
			.ground(ground)
			.period(period)
			.ageGroup(ageGroup)
			.detail(detail)
			.hirePlayerNumber(hirePlayerNumber)
			.build();
		Team team = Team
			.builder()
			.id(1L)
			.sport(new Sports(1L, "축구"))
			.name("팀이름1")
			.bio("팀소개1")
			.logo("팀로고1")
			.ageGroup(AgeGroup.TWENTIES)
			.build();

		given(hirePostRepository.findById(anyLong())).willReturn(Optional.of(hirePost));
		HirePostInfoResponse response = new HirePostInfoResponse(
			postId,
			city.getName(),
			region.getName(),
			ground.getName(),
			position,
			ageGroup,
			hirePlayerNumber,
			detail,
			period.getDate(),
			period.getStartTime(),
			period.getEndTime(),
			team.getId(),
			team.getName(),
			team.getLogo(),
			team.getMannerTemperature(),
			1L,
			"쭝"
		);
		given(hirePostConverter.convertToHirePostInfoResponse(hirePost)).willReturn(response);

		// when
		HirePostInfoResponse hirePostInfoResponse = hirePostService.getHirePost(1L);

		// then
		verify(hirePostRepository, times(1)).findById(anyLong());
		verify(hirePostConverter, times(1)).convertToHirePostInfoResponse(any());

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(hirePostInfoResponse.getPostId()).isEqualTo(1L);
		softAssertions.assertThat(hirePostInfoResponse.getPosition()).isEqualTo("윙백");
		softAssertions.assertThat(hirePostInfoResponse.getTeamId()).isEqualTo(1L);
		softAssertions.assertThat(hirePostInfoResponse.getTeamName()).isEqualTo("팀이름1");
		softAssertions.assertThat(hirePostInfoResponse.getTeamCaptainId()).isEqualTo(1L);
		softAssertions.assertThat(hirePostInfoResponse.getTeamCaptainName()).isEqualTo("쭝");
		softAssertions.assertAll();
	}
}
