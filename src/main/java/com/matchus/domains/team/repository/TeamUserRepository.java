package com.matchus.domains.team.repository;

import com.matchus.domains.team.domain.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {

}
