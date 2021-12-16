package com.matchus.domains.tag.repository;

import com.matchus.domains.tag.domain.TeamTag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamTagRepository extends JpaRepository<TeamTag, Long> {

	List<TeamTag> findAllByTeamId(Long teamID);

	Optional<TeamTag> findByTeamIdAndTagId(Long reviewedTeamId, Long tagId);
}
