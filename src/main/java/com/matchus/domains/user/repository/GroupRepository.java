package com.matchus.domains.user.repository;

import com.matchus.domains.user.domain.Group;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

	Optional<Group> findByName(String name);

}
