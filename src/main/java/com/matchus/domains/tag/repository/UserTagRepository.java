package com.matchus.domains.tag.repository;

import com.matchus.domains.tag.domain.UserTag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {

	List<UserTag> findAllByUserId(Long userId);

	Optional<UserTag> findByUserIdAndTagId(Long id, Long tagId);
}
