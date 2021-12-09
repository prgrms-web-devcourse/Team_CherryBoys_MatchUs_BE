package com.matchus.domains.hire.repository;


import static org.assertj.core.api.Assertions.*;

import com.matchus.domains.common.Address;
import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.common.repository.SportsRepository;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponseDto;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.repository.TeamRepository;
import com.matchus.global.config.QuerydslConfig;
import com.matchus.global.utils.PageRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(QuerydslConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
public class HirePostRepositoryTest {

	@Autowired
	private HirePostRepository hirePostRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private SportsRepository sportsRepository;

	@DisplayName("용병 구인 게시글 첫 번째 페이지 조회 테스트")
	@Test
	void findAllNoOffsetFirstTest() {
		// given
		Long lastId = null;
		int size = 10;

		for (int i = 1; i <= 30; i++) {
			hirePostRepository.save(
				HirePost
					.builder()
					.title("구인글제목1")
					.position("윙백")
					.address(new Address("서울", "광진구", "아차산풋살장"))
					.period(
						new Period(
							LocalDate.of(2021, 12, 6),
							LocalTime.of(12, 30),
							LocalTime.of(14, 30)
						)
					)
					.ageGroup(AgeGroup.TEENAGER)
					.detail("세부내용")
					.hirePlayerNumber(3)
					.build()
			);
		}

		// when
		List<HirePost> hirePosts = hirePostRepository.findAllNoOffset(lastId, size);

		// then
		assertThat(hirePosts).hasSize(10);
		assertThat(hirePosts.get(0).getId()).isEqualTo(30L);
		assertThat(hirePosts.get(9).getId()).isEqualTo(21L);
	}

	@DisplayName("용병 구인 게시글 두 번째 페이지 조회 테스트")
	@Test
	void findAllNoOffsetSecondTest() {
		// given
		Long lastId = 21L;
		int size = 10;

		for (int i = 1; i <= 30; i++) {
			hirePostRepository.save(
				HirePost
					.builder()
					.title("구인글제목1")
					.position("윙백")
					.address(new Address("서울", "광진구", "아차산풋살장"))
					.period(
						new Period(
							LocalDate.of(2021, 12, 6),
							LocalTime.of(12, 30),
							LocalTime.of(14, 30)
						)
					)
					.ageGroup(AgeGroup.TEENAGER)
					.detail("세부내용")
					.hirePlayerNumber(3)
					.build()
			);
		}

		// when
		List<HirePost> hirePosts = hirePostRepository.findAllNoOffset(lastId, size);

		// then
		assertThat(hirePosts).hasSize(10);
		assertThat(hirePosts.get(0).getId()).isEqualTo(20L);
		assertThat(hirePosts.get(9).getId()).isEqualTo(11L);
	}

	@DisplayName("용병 게시글 리스트 필터 조회 테스트")
	@Test
	void hirePostsByFilterTest() {
		// given
		String position = "윙백";
		Long sportsId = null;
		AgeGroup ageGroup = null;
		String city = null;
		String region = null;
		String groundName = null;
		LocalDate date = null;
		Long lastId = null;
		int size = 30;
		PageRequest pageRequest = new PageRequest(lastId, size);

		Sports firstSports = sportsRepository.save(new Sports(1L, "축구"));
		Sports secondSports = sportsRepository.save(new Sports(2L, "풋살"));
		Team teamOne = teamRepository.save(
			Team
				.builder()
				.sport(firstSports)
				.name("팀이름1")
				.bio("팀소개1")
				.logo("팀로고1")
				.ageGroup(AgeGroup.TWENTIES)
				.build()
		);
		Team teamTwo = teamRepository.save(
			Team
				.builder()
				.sport(secondSports)
				.name("팀이름2")
				.bio("팀소개2")
				.logo("팀로고2")
				.ageGroup(AgeGroup.TWENTIES)
				.build()
		);

		for (int i = 1; i <= 10; i++) {
			HirePost hirePost = hirePostRepository.save(
				HirePost
					.builder()
					.title("구인글제목1")
					.position("윙백")
					.address(new Address("서울", "광진구", "아차산풋살장"))
					.period(
						new Period(
							LocalDate.of(2021, 12, 6),
							LocalTime.of(12, 30),
							LocalTime.of(14, 30)
						)
					)
					.ageGroup(AgeGroup.TEENAGER)
					.detail("세부내용")
					.hirePlayerNumber(3)
					.build()
			);
			hirePost.setTeam(teamOne);
		}
		for (int i = 1; i <= 10; i++) {
			HirePost hirePost = hirePostRepository.save(
				HirePost
					.builder()
					.title("구인글제목2")
					.position("수비수")
					.address(new Address("서울", "광진구", "아차산풋살장"))
					.period(
						new Period(
							LocalDate.of(2021, 12, 6),
							LocalTime.of(12, 30),
							LocalTime.of(14, 30)
						)
					)
					.ageGroup(AgeGroup.TEENAGER)
					.detail("세부내용")
					.hirePlayerNumber(3)
					.build()
			);
			hirePost.setTeam(teamTwo);
		}
		for (int i = 1; i <= 10; i++) {
			HirePost hirePost = hirePostRepository.save(
				HirePost
					.builder()
					.title("구인글제목3")
					.position("수비수")
					.address(new Address("경기도", "남양주시", "오남풋살장"))
					.period(
						new Period(
							LocalDate.of(2021, 12, 6),
							LocalTime.of(12, 30),
							LocalTime.of(14, 30)
						)
					)
					.ageGroup(AgeGroup.TEENAGER)
					.detail("세부내용")
					.hirePlayerNumber(3)
					.build()
			);
			hirePost.setTeam(teamOne);
		}

		// when
		List<HirePostListFilterResponseDto> hirePostsNoOffsetByFilter = hirePostRepository.findAllNoOffsetByFilter(
			position,
			sportsId,
			ageGroup,
			city,
			region,
			groundName,
			date,
			pageRequest
		);

		// then
		assertThat(hirePostsNoOffsetByFilter).hasSize(10);
	}
}
