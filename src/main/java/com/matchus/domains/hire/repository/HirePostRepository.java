package com.matchus.domains.hire.repository;

import com.matchus.domains.hire.domain.HirePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HirePostRepository
	extends JpaRepository<HirePost, Long>, HirePostRepositoryCustom {

}
