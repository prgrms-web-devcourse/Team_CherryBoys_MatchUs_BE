package com.matchus.domains.common.repository;

import com.matchus.domains.sports.domain.Sports;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportsRepository extends JpaRepository<Sports, Long> {

	Optional<Sports> findByName(String name);

}
