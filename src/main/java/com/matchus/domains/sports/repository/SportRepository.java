package com.matchus.domains.sports.repository;

import com.matchus.domains.sports.domain.Sports;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sports, Long> {

	Optional<Sports> findByName(String sportName);

}
