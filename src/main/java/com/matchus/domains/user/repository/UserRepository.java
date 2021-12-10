package com.matchus.domains.user.repository;

import com.matchus.domains.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailAndIsDisaffiliatedFalse(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

}
