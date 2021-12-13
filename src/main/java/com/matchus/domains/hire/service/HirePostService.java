package com.matchus.domains.hire.service;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.hire.converter.HirePostConverter;
import com.matchus.domains.hire.domain.HireApplication;
import com.matchus.domains.hire.domain.HireApplyUser;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.hire.dto.request.HirePostModifyRequest;
import com.matchus.domains.hire.dto.request.HirePostRetrieveFilterRequest;
import com.matchus.domains.hire.dto.request.HirePostWriteRequest;
import com.matchus.domains.hire.dto.response.HireApplicationsResponse;
import com.matchus.domains.hire.dto.response.HirePostInfoResponse;
import com.matchus.domains.hire.dto.response.HirePostListFilterResponseDto;
import com.matchus.domains.hire.dto.response.HirePostModifyResponse;
import com.matchus.domains.hire.dto.response.HirePostRetrieveByFilterResponse;
import com.matchus.domains.hire.dto.response.HirePostWriteResponse;
import com.matchus.domains.hire.exception.HirePostNotFoundException;
import com.matchus.domains.hire.repository.HirePostRepository;
import com.matchus.domains.location.domain.Location;
import com.matchus.domains.location.service.LocationService;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.sports.service.SportsService;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.service.TeamService;
import com.matchus.domains.user.domain.User;
import com.matchus.global.error.ErrorCode;
import com.matchus.global.utils.PageRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class HirePostService {

	private final HirePostRepository hirePostRepository;
	private final SportsService sportsService;
	private final HirePostConverter hirePostConverter;
	private final TeamService teamService;
	private final LocationService locationService;
	private final HireApplicationService hireApplicationService;

	@Transactional(readOnly = true)
	public HirePostRetrieveByFilterResponse retrieveHirePostsNoOffsetByFilter(
		HirePostRetrieveFilterRequest filterRequest,
		PageRequest pageRequest
	) {
		Sports sports = sportsService.getSportsOrNull(filterRequest.getSports());
		AgeGroup ageGroup = AgeGroup.findGroupOrNull(filterRequest.getAgeGroup());

		List<HirePostListFilterResponseDto> hirePosts = hirePostRepository.findAllNoOffsetByFilter(
			filterRequest.getPosition(),
			sports == null ? null : sports.getId(),
			ageGroup,
			filterRequest.getCityId(),
			filterRequest.getRegionId(),
			filterRequest.getGroundId(),
			filterRequest.getDate() == null ? null : LocalDate.parse(filterRequest.getDate()),
			pageRequest
		);

		return new HirePostRetrieveByFilterResponse(hirePosts);
	}

	@Transactional(readOnly = true)
	public List<HirePost> retrieveHirePostsNoOffset(
		Long lastId,
		int size
	) {
		return hirePostRepository.findAllNoOffset(lastId, size);
	}

	@Transactional(readOnly = true)
	public HirePostInfoResponse getHirePost(Long postId) {
		return hirePostConverter.convertToHirePostInfoResponse(findHirePost(postId));
	}

	public HirePostWriteResponse writeHirePost(HirePostWriteRequest request) {
		Team team = teamService.findExistingTeam(request.getTeamId());
		Location location = locationService.getLocation(
			request.getCityId(),
			request.getRegionId(),
			request.getGroundId()
		);
		AgeGroup ageGroup = AgeGroup.findGroup(request.getAgeGroup());

		HirePost hirePost = hirePostConverter.convertToHirePost(request, location, ageGroup);
		hirePost.setTeam(team);

		HirePost savedHirePost = hirePostRepository.save(hirePost);
		return new HirePostWriteResponse(savedHirePost.getId());
	}

	public HirePostModifyResponse modifyHirePost(Long postId, HirePostModifyRequest request) {
		Location location = locationService.getLocation(
			request.getCityId(),
			request.getRegionId(),
			request.getGroundId()
		);
		AgeGroup ageGroup = AgeGroup.findGroup(request.getAgeGroup());

		HirePost hirePost = findHirePost(postId);
		hirePost.changeInfo(
			request.getPosition(),
			location.getCity(),
			location.getRegion(),
			location.getGround(),
			new Period(request.getDate(), request.getStartTime(), request.getEndTime()),
			ageGroup,
			request.getDetail(),
			request.getHirePlayerNumber()
		);

		return new HirePostModifyResponse(hirePost.getId());
	}

	public void removeHirePost(Long postId) {
		hirePostRepository.deleteById(postId);
	}

	@Transactional(readOnly = true)
	public HirePost findHirePost(Long postId) {
		return hirePostRepository
			.findById(postId)
			.orElseThrow(() -> new HirePostNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public HireApplicationsResponse getHireApplications(Long postId) {
		List<HireApplication> applications =
			hireApplicationService.getHireApplicationsByHirePostId(postId);

		List<HireApplyUser> applyUsers = applications
			.stream()
			.map(
				hireApplication -> {
					User user = hireApplication.getUser();
					return new HireApplyUser(
						hireApplication.getId(),
						user.getId(),
						user.getNickname()
					);
				}
			).collect(Collectors.toList());

		return new HireApplicationsResponse(applyUsers);
	}
}
