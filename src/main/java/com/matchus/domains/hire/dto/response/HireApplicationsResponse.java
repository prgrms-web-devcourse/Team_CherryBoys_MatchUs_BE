package com.matchus.domains.hire.dto.response;

import com.matchus.domains.hire.domain.HireApplyUser;
import java.util.List;
import lombok.Getter;

@Getter
public class HireApplicationsResponse {

	private final List<HireApplyUser> applications;

	public HireApplicationsResponse(List<HireApplyUser> applyUsers) {
		this.applications = applyUsers;
	}
}
