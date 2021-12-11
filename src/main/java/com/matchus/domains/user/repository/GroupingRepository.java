package com.matchus.domains.user.repository;

import com.matchus.domains.user.domain.Grouping;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupingRepository extends JpaRepository<Grouping, Long> {

	Optional<Grouping> findByName(String name);

}
