package com.matchus.domains.team.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Sports;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.dto.request.TeamCreateRequest;
import com.matchus.domains.team.service.TeamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@WebMvcTest(TeamController.class)
class TeamControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TeamService teamService;

	@DisplayName("팀 생성 테스트")
	@Test
	void createTeamTest() throws Exception {
		// given
		MockMultipartFile file = new MockMultipartFile(
			"file",
			"hello.jpg",
			MediaType.MULTIPART_FORM_DATA_VALUE,
			"Hello, World!".getBytes()
		);
		String teamName = "teamName";
		String bio = "안녕";
		String sports = "축구";
		String ageGroup = "20대";
		TeamCreateRequest request = new TeamCreateRequest(file, teamName, bio, sports, ageGroup);
		Team team = Team
			.builder()
			.id(1L)
			.sport(new Sports(1L, "축구"))
			.name("팀이름")
			.bio("팀소개")
			.logo("팀로고.jpg")
			.ageGroup(AgeGroup.TWENTIES)
			.build();
		given(teamService.createTeam(request)).willReturn(team);

		// when
		ResultActions result = mockMvc
			.perform(
				post("/teams")
					.contentType(MediaType.MULTIPART_FORM_DATA)
			);

		// then
		result
			.andDo(print())
			.andExpectAll(
				status().isOk()
			);
	}
}
