package com.matchus.domains.hire.service;

import com.matchus.domains.hire.domain.HireApplication;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.request.HireApplyRequest;
import com.matchus.domains.hire.dto.response.HireApplyResponse;
import com.matchus.domains.hire.repository.HireApplicationRepository;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class HireApplicationService {

	private final HireApplicationRepository hireApplicationRepository;
	private final UserService userService;
	private final HirePostService hirePostService;

	public HireApplyResponse applyHire(HireApplyRequest request, String userEmail) {
		User user = userService.findActiveUser(userEmail);
		HirePost hirePost = hirePostService.findHirePost(request.getPostId());

		HireApplication hireApplication = hireApplicationRepository.save(
			HireApplication
				.builder()
				.hirePost(hirePost)
				.user(user)
				.build()
		);

		return new HireApplyResponse(hireApplication.getId());
	}
}
