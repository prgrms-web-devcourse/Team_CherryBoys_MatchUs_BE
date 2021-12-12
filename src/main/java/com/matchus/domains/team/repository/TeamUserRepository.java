package com.matchus.domains.team.repository;

import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.TeamUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {

	List<TeamUser> findAllByTeamIdAndGradeNot(Long teamId, Grade grade);
}
