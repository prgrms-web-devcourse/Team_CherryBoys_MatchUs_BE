package com.matchus.domains.hire.repository;


import static org.assertj.core.api.Assertions.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.response.HirePostListFilterResult;
import com.matchus.domains.location.domain.City;
import com.matchus.domains.location.domain.Ground;
import com.matchus.domains.location.domain.Region;
import com.matchus.domains.location.repository.CityRepository;
import com.matchus.domains.location.repository.GroundRepository;
import com.matchus.domains.location.repository.RegionRepository;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.repository.SportRepository;
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
	private SportRepository sportRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private GroundRepository groundRepository;

	@DisplayName("용병 게시글 리스트 필터 조회 성공 테스트")
	@Test
	void hirePostsByFilterTest() {
		// given
		String position = "윙백";
		Long sportsId = null;
		Long cityId = null;
		Long regionId = null;
		Long groundId = null;
		AgeGroup ageGroup = null;
		LocalDate date = null;
		Long lastId = null;
		int size = 30;
		PageRequest pageRequest = new PageRequest(lastId, size);

		Sports firstSports = sportRepository.save(new Sports(1L, "축구"));
		Sports secondSports = sportRepository.save(new Sports(2L, "풋살"));
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

		City city = cityRepository.save(new City(1L, "서울특별시"));
		Region region = regionRepository.save(new Region(1L, city, "강남구"));
		Ground ground = groundRepository.save(new Ground(1L, region, "대륭축구장"));
		for (int i = 1; i <= 10; i++) {
			HirePost hirePost = hirePostRepository.save(
				HirePost
					.builder()
					.position("윙백")
					.city(city)
					.region(region)
					.ground(ground)
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
					.position("수비수")
					.city(city)
					.region(region)
					.ground(ground)
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
					.position("수비수")
					.city(city)
					.region(region)
					.ground(ground)
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
		List<HirePostListFilterResult> hirePostsNoOffsetByFilter = hirePostRepository.findAllNoOffsetByFilter(
			position,
			sportsId,
			ageGroup,
			cityId,
			regionId,
			groundId,
			date,
			pageRequest
		);

		// then
		assertThat(hirePostsNoOffsetByFilter).hasSize(10);
	}
}
