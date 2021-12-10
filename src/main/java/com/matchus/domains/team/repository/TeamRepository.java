package com.matchus.domains.team.repository;

import com.matchus.domains.team.domain.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Optional<Team> findByIdAndIsDeletedFalse(Long teamId);
}
