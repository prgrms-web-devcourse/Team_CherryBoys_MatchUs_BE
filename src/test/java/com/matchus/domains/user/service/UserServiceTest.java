package com.matchus.domains.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.dto.SuccessResponse;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.service.TeamUserService;
import com.matchus.domains.user.converter.UserConverter;
import com.matchus.domains.user.domain.Gender;
import com.matchus.domains.user.domain.Group;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserConverter userConverter;

	@Mock
	private GroupService groupService;

	@Mock
	private TeamUserService teamUserService;

	@InjectMocks
	private UserService userService;

	@Mock
	private SportsService sportsService;

	@Test
	@DisplayName("이메일 중복 여부 테스트")
	void checkEmail() {
		//given
		final String email = "abc@gmail.com";
		given(userRepository.existsByEmail(any(String.class)))
			.willReturn(true);

		//when
		SuccessResponse successResponse = userService.checkEmail(email);

		//then
		assertThat(successResponse.isIsduplicated()).isTrue();
	}

	@Test
	@DisplayName("닉네임중복 여부 테스트")
	void checkNickname() {
		//given
		final String nickname = "머쓱머쓱";
		given(userRepository.existsByNickname(nickname))
			.willReturn(false);

		//when
		SuccessResponse successResponse = userService.checkNickname(nickname);

		//then
		assertThat(successResponse.isIsduplicated()).isFalse();
	}

	@Test
	@DisplayName("유저 탈퇴 요청 테스트")
	void deactivateUser() {
		//given
		final String email = "abc@gmail.com";
		given(userRepository.findByEmailAndIsDisaffiliatedFalse(any(String.class)))
			.willReturn(any());

		//when
		userService.deactivateUser(email);

		//then
		verify(userRepository, times(1)).save(any(User.class));

	}

	@Test
	@DisplayName("회원가입 성공 테스트")
	void SignUpTest() {
		//given
		final SignUpRequest signUpRequest = SignUpRequest
			.builder()
			.name("오재원")
			.email("abc@abc.com")
			.nickname("채리")
			.password("password")
			.gender("MAN")
			.ageGroup("20대")
			.sports("축구")
			.bio("안녕세요")
			.build();

		final Sports sports = new Sports(1L, "축구");

		final Group group = new Group(1L, "USER_GROUP");

		final User user = User
			.builder()
			.id(1L)
			.name("오재원")
			.email("abc@abc.com")
			.nickname("채리")
			.password("password")
			.gender(Gender.MAN)
			.ageGroup(AgeGroup.TWENTIES)
			.sport(sports)
			.bio("안녕세요")
			.group(group)
			.build();

		given(sportsService.getSports(any(String.class)))
			.willReturn(sports);
		given(groupService.findByName(any(String.class)))
			.willReturn(group);

		given(userConverter.convertToUser(any(), any(),
										  any(), any()
		))
			.willReturn(user);

		//when
		userService.signUp(signUpRequest);

		//then
		verify(userRepository, times(1)).save(any(User.class));

	}

}
