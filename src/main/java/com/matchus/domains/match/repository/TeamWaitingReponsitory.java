package com.matchus.domains.match.repository;

import com.matchus.domains.match.domain.TeamWaiting;
import com.matchus.domains.match.domain.WaitingType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamWaitingReponsitory extends JpaRepository<TeamWaiting, Long> {

	Optional<TeamWaiting> findByIdAndTypeNot(Long matchId, WaitingType type);

	Optional<TeamWaiting> findByMatchIdAndType(Long matchId, WaitingType type);

	List<TeamWaiting> findAllByMatchIdAndType(Long matchId, WaitingType type);

}
