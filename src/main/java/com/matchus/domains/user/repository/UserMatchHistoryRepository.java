package com.matchus.domains.user.repository;

import com.matchus.domains.user.domain.UserMatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMatchHistoryRepository extends JpaRepository<UserMatchHistory, Long> {

}
