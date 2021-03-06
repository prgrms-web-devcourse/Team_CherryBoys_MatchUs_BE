package com.matchus.domains.team.repository;

import com.matchus.domains.team.domain.Grade;
import com.matchus.domains.team.domain.Team;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.domains.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {

	List<TeamUser> findAllByUserId(Long userId);

	List<TeamUser> findAllByTeamId(Long teamId);

	List<TeamUser> findAllByTeamIdAndGradeNot(Long teamId, Grade grade);

	List<TeamUser> findAllByTeamIdAndGrade(Long teamId, Grade grade);

	List<TeamUser> findAllByUserIdAndGradeIn(Long teamId, List<Grade> grades);

	Optional<TeamUser> findByTeamIdAndUserId(Long teamId, Long userId);

	void deleteByTeamIdAndUserId(Long teamId, Long userId);

	boolean existsByUserAndTeam(User user, Team team);
}
