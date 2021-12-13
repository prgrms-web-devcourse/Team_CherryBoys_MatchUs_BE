package com.matchus.domains.hire.repository;

import com.matchus.domains.hire.domain.HireApplication;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HireApplicationRepository extends JpaRepository<HireApplication, Long> {

	List<HireApplication> findAllByHirePostId(Long hirePostId);
}
