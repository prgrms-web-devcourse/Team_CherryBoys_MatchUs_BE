package com.matchus.domains.team.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.converter.TeamConverter;
import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.dto.request.TeamModifyRequest;
import com.matchus.domains.team.dto.response.TeamCreateResponse;
import com.matchus.domains.team.dto.response.TeamModifyResponse;
import com.matchus.domains.team.repository.TeamRepository;
import com.matchus.domains.user.domain.Gender;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.repository.UserRepository;
import com.matchus.global.service.FileUploadService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TeamServiceTest<TeamUserRepository> {

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
	private com.matchus.domains.team.repository.TeamUserRepository teamUserRepository;

	@Mock
	private UserRepository userRepository;

	@DisplayName("팀 생성 서비스 성공 테스트")
	@Test
	void createTeamTest() {
		MockMultipartFile file = new MockMultipartFile(
			"file", "originalFilename", "multipart/form-data", "file".getBytes()
		);
		String teamName = "팀이름";
		String bio = "팀소개";
		String sports = "축구";
		String ageGroup = "20대";
		TeamCreateRequest request = new TeamCreateRequest(file, teamName, bio, sports, ageGroup);
		Sports sport = new Sports(1L, "축구");
		String logo = "팀로고.jpg";
		String email = "이메일@email.com";
		User user = User
			.builder()
			.id(1L)
			.sport(sport)
			.email(email)
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
		given(sportsService.getSports(any())).willReturn(sport);
		given(uploadService.uploadImage(any())).willReturn(logo);
		given(userRepository.findByEmailAndIsDisaffiliatedFalse(any())).willReturn(
			Optional.of(user));
		given(teamConverter.convertToTeam(any(), any(), any())).willReturn(team);
		given(teamRepository.save(any())).willReturn(team);
		given(teamUserRepository.save(any())).willReturn(teamUser);

		// when
		TeamCreateResponse response = teamService.createTeam(request, email);

		// then
		assertThat(response.getTeamId()).isEqualTo(1L);

		verify(sportsService, times(1)).getSports(any());
		verify(uploadService, times(1)).uploadImage(any());
		verify(teamConverter, times(1)).convertToTeam(any(), any(), any());
		verify(teamRepository, times(1)).save(any());
		verify(teamUserRepository, times(1)).save(any());
	}

	@DisplayName("팀 정보 수정 서비스 성공 테스트")
	@Test
	void modifyTeamTest() {
		// given
		Long teamId = 1L;
		String logo = "modifiedImage.jpg";
		given(uploadService.uploadImage(any())).willReturn(logo);
		Sports sport = new Sports(1L, "축구");
		String name = "teamName";
		String originBio = "originBio";
		Team team = Team
			.builder()
			.id(1L)
			.sport(sport)
			.name(name)
			.bio(originBio)
			.logo(logo)
			.ageGroup(AgeGroup.TWENTIES)
			.build();
		given(teamRepository.findByIdAndIsDeletedFalse(any(Long.class)))
			.willReturn(Optional.of(team));

		MockMultipartFile file = new MockMultipartFile(
			"file", "imageFileName", "multipart/form-data", "file".getBytes()
		);
		String bio = "modifiedBio";
		String ageGroup = "30대";
		TeamModifyRequest request = new TeamModifyRequest(file, bio, ageGroup);

		// when
		TeamModifyResponse teamModifyResponse = teamService.modifyTeam(teamId, request);

		// then
		assertThat(teamModifyResponse.getTeamId()).isEqualTo(1L);

		verify(uploadService, times(1)).uploadImage(any(MultipartFile.class));
		verify(teamRepository, times(1)).findByIdAndIsDeletedFalse(any(Long.class));
	}
}
