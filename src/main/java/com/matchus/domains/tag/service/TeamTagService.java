package com.matchus.domains.tag.service;

import com.matchus.domains.tag.domain.Tag;
import com.matchus.domains.tag.domain.TagType;
import com.matchus.domains.tag.domain.TeamTag;
import com.matchus.domains.tag.repository.TeamTagRepository;
import com.matchus.domains.team.domain.Team;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamTagService {

	private final TeamTagRepository teamTagRepository;
	private final TagService tagService;

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
						Tag tag = tagService.getTag(tagId);
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
