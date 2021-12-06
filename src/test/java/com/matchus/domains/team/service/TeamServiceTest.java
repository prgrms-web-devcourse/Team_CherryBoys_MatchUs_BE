package com.matchus.domains.team.service;

import static org.mockito.BDDMockito.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Sports;
import com.matchus.domains.common.service.SportsService;
import com.matchus.domains.team.converter.TeamConverter;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.repository.TeamRepository;
import com.matchus.domains.team.repository.TeamUserRepository;
import com.matchus.domains.user.domain.Gender;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.service.FileUploadService;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

	@InjectMocks
	private TeamService teamService;

	@Mock
	private TeamConverter teamConverter;

	@Mock
	private TeamRepository teamRepository;

	@Mock
	private SportsService sportsService;

	@Mock
	private FileUploadService uploadService;

	@Mock
	private TeamUserRepository teamUserRepository;

	@Mock
	private UserRepository userRepository;

	@DisplayName("팀 생성 서비스 테스트")
	@Test
	void createTeamTest() {
		// given
		MockMultipartFile file = new MockMultipartFile(
			"file", "originalFilename", "multipart/form-data", "file".getBytes()
		);
		String teamName = "팀이름";
		String bio = "팀소개";
		String sports = "축구";
		String ageGroup = "20대";
		TeamCreateRequest request = new TeamCreateRequest(file, teamName, bio, sports, ageGroup);
		Sports sport = new Sports(1L, "축구");
		String logo = "logoimage.jpg";
		User user = User
			.builder()
			.id(1L)
			.sport(sport)
			.email("이메일@email.com")
			.name("이름")
			.password("비밀번호")
			.nickname("닉네임")
			.gender(Gender.MAN)
			.ageGroup(AgeGroup.TWENTIES)
			.build();
		Team team = Team
			.builder()
			.id(1L)
			.sport(sport)
			.name(teamName)
			.bio(bio)
			.logo(logo)
			.ageGroup(AgeGroup.TWENTIES)
			.build();
		TeamUser teamUser = TeamUser
			.builder()
			.team(team)
			.user(user)
			.grade(Grade.CAPTAIN)
			.build();
		given(sportsService.findByName(any())).willReturn(sport);
		given(uploadService.uploadImage(any())).willReturn(logo);
		given(userRepository.findById(any())).willReturn(Optional.of(user));
		given(teamConverter.convertToTeam(any(), any(), any())).willReturn(team);
		given(teamRepository.save(any())).willReturn(team);
		given(teamUserRepository.save(any())).willReturn(teamUser);

		// when
		Team createdTeam = teamService.createTeam(request);

		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(createdTeam.getId()).isEqualTo(1L);
		softAssertions.assertThat(createdTeam.getSport().getName()).isEqualTo(sports);
		softAssertions.assertThat(createdTeam.getName()).isEqualTo(teamName);
		softAssertions.assertThat(createdTeam.getBio()).isEqualTo(bio);
		softAssertions.assertThat(createdTeam.getLogo()).isEqualTo(logo);
		softAssertions.assertThat(createdTeam.getAgeGroup()).isEqualTo(AgeGroup.TWENTIES);
		softAssertions.assertAll();

		verify(sportsService, times(1)).findByName(any());
		verify(uploadService, times(1)).uploadImage(any());
		verify(teamConverter, times(1)).convertToTeam(any(), any(), any());
		verify(teamRepository, times(1)).save(any());
		verify(teamUserRepository, times(1)).save(any());
	}

}
