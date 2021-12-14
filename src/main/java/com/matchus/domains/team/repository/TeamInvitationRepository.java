package com.matchus.domains.team.repository;

import com.matchus.domains.team.domain.TeamInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {

	boolean existsByTeamIdAndUserId(Long teamId, Long userId);
}
