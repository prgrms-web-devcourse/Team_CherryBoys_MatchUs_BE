package com.matchus.domains.user.repository;

import com.matchus.domains.user.domain.Role;
import com.matchus.domains.user.domain.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRespository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleName);

}
