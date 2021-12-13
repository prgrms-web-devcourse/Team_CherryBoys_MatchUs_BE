package com.matchus.domains.hire.service;

import com.matchus.domains.hire.domain.HireApplication;
import com.matchus.domains.hire.repository.HireApplicationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class HireApplicationService {

	private final HireApplicationRepository hireApplicationRepository;

	@Transactional(readOnly = true)
	public List<HireApplication> getHireApplicationsByHirePostId(Long hirePostId) {
		return hireApplicationRepository.findAllByHirePostId(hirePostId);
	}
}
