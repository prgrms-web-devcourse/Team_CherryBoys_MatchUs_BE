package com.matchus.domains.match.repository;

import com.matchus.domains.match.domain.MemberWaiting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberWaitingReponsitory extends JpaRepository<MemberWaiting, Long> {

	List<MemberWaiting> findAllByTeamWaitingId(Long teamWaitingId);

	void deleteAllByTeamWaitingId(Long teamWaitingId);

}
