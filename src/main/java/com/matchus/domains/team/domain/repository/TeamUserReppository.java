package com.matchus.domains.team.domain.repository;

import com.matchus.domains.team.domain.TeamUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserReppository extends JpaRepository<TeamUser, Long> {

	List<TeamUser> findAllByUserId(Long userId);
}

