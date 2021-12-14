package com.matchus.domains.hire.dto.request;

import com.matchus.domains.hire.domain.HireApplyUser;
import java.util.List;
import lombok.Getter;

@Getter
public class ApplicationsAcceptRequest {

	private List<HireApplyUser> applications;

	public ApplicationsAcceptRequest() {
	}

	public ApplicationsAcceptRequest(List<HireApplyUser> applications) {
		this.applications = applications;
	}
}
