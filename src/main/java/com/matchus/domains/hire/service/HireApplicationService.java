package com.matchus.domains.hire.service;

import com.matchus.domains.hire.domain.HireApplication;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.request.HireApplyRequest;
import com.matchus.domains.hire.dto.response.HireApplyResponse;
import com.matchus.domains.hire.exception.HireApplicationAlreadyExistsException;
import com.matchus.domains.hire.exception.HireApplicationNotFoundException;
import com.matchus.domains.hire.exception.InvalidHireApplicationException;
import com.matchus.domains.hire.repository.HireApplicationRepository;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.service.UserService;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.response.SuccessResponse;
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

		boolean isAffiliatedTeam = user
			.getTeamUsers()
			.stream()
			.anyMatch(teamUser -> teamUser.getTeam().getId().equals(hirePost.getTeam().getId()));
		if (isAffiliatedTeam) {
			throw new InvalidHireApplicationException(ErrorCode.INVALID_HIRE_APPLICATION);
		}

		if (hireApplicationRepository.existsByHirePostIdAndUserId(hirePost.getId(), user.getId())) {
			throw new HireApplicationAlreadyExistsException(ErrorCode.HIRE_APPLICATION_ALREADY_EXISTS);
		}

		HireApplication hireApplication = hireApplicationRepository.save(
			HireApplication
				.builder()
				.hirePost(hirePost)
				.user(user)
				.build()
		);

		return new HireApplyResponse(hireApplication.getId());
	}

	public SuccessResponse cancelApplication(Long applicationsId) {
		HireApplication hireApplication = hireApplicationRepository
			.findById(applicationsId)
			.orElseThrow(() -> new HireApplicationNotFoundException(ErrorCode.HIRE_APPLICATION_NOT_FOUND));

		hireApplicationRepository.delete(hireApplication);

		return new SuccessResponse(true);
	}
}
