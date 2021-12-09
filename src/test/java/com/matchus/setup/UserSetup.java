package com.matchus.setup;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.user.domain.Gender;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.repository.UserRepository;
import org.mockito.Mock;
import org.springframework.stereotype.Component;

@Component
public class UserSetup {

	@Mock
	private UserRepository userRepository;

/*	public Group saveGroup() {
		new Group(1L, "USER_GROUP")
	}*/

	public User saveUser() {
		return userRepository.save(User
									   .builder()
									   .name("오재원")
									   .email("abc@abc.com")
									   .nickname("채리")
									   .password("password")
									   .gender(Gender.MAN)
									   .ageGroup(AgeGroup.FIFTIES)
									   .sport(Sports
												  .builder()
												  .id(1L)
												  .name("축구")
												  .build())
									   .bio("안녕세요")
									   .build());
	}

}
