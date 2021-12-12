package com.matchus.domains.tag.service;

import com.matchus.domains.tag.domain.TeamTag;
import com.matchus.domains.tag.repository.TeamTagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamTagService {

	private final TeamTagRepository teamTagRepository;

	@Transactional(readOnly = true)
	public List<TeamTag> getTeamTags(Long teamId) {
		return teamTagRepository.findAllByTeamId(teamId);
	}

}
