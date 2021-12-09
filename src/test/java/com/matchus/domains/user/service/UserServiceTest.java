package com.matchus.domains.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.matchus.domains.common.dto.SuccessResponse;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.service.TeamUserService;
import com.matchus.domains.user.converter.UserConverter;
import com.matchus.domains.user.domain.Group;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.dto.SignUpRequest;
import com.matchus.domains.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Spy
	private BCryptPasswordEncoder passwordEncoder;


	@Test
	@DisplayName("회원가입 성공 테스트")
	void SignUpTest() {
		//given
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

		given(sportsService.getSports(signUpRequest.getSports()))
			.willReturn(any(Sports.class));
		given(groupService.findByName(any(String.class)))
			.willReturn(any(Group.class));

		final String encryptedPw = encoder.encode(signUpRequest.getPassword());

		//when
		userService.signUp(signUpRequest);

		//then
		verify(userRepository, times(1)).save(any(User.class));
		verify(passwordEncoder, times(1)).encode(any(String.class));

	}

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
		assertThat(successResponse.isSuccess()).isTrue();
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
		assertThat(successResponse.isSuccess()).isFalse();
	}

}
