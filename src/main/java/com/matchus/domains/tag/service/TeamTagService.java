package com.matchus.domains.tag.service;

import com.matchus.domains.tag.domain.Tag;
import com.matchus.domains.tag.domain.TagType;
import com.matchus.domains.tag.domain.TeamTag;
import com.matchus.domains.tag.exception.TagNotFoundException;
import com.matchus.domains.tag.repository.TagRepository;
import com.matchus.domains.tag.repository.TeamTagRepository;
import com.matchus.domains.team.domain.Team;
import com.matchus.global.error.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamTagService {

	private final TeamTagRepository teamTagRepository;
	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
	public List<TeamTag> getTeamTags(Long teamId) {
		return teamTagRepository.findAllByTeamId(teamId);
	}

	public void calculateTeamTags(Team reviewedTeam, List<Long> tagIds) {
		for (Long tagId : tagIds) {
			teamTagRepository
				.findByTeamIdAndTagId(reviewedTeam.getId(), tagId)
				.ifPresentOrElse(
					teamTag -> {
						teamTag.increaseTagCount();
						calculateTeamMannerTemperature(teamTag.getTag().getType(), reviewedTeam);
					},
					() -> {
						Tag tag = tagRepository
							.findById(tagId)
							.orElseThrow(() -> new TagNotFoundException(ErrorCode.TAG_NOT_FOUND));
						teamTagRepository.save(
							TeamTag
								.builder()
								.tag(tag)
								.team(reviewedTeam)
								.build()
						);
						calculateTeamMannerTemperature(tag.getType(), reviewedTeam);
					}
				);
		}
	}

	private void calculateTeamMannerTemperature(TagType tagType, Team reviewedTeam) {
		reviewedTeam.updateMannerTemperature(
			tagType.calculate(reviewedTeam.getMannerTemperature())
		);
	}
}
