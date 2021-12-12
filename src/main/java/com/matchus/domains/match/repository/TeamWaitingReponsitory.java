package com.matchus.domains.match.repository;

import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.domain.WaitingType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamWaitingReponsitory extends JpaRepository<TeamWaiting, Long> {

	Optional<TeamWaiting> findByMatchIdAndType(Long matchId, WaitingType type);

}
