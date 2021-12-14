package com.matchus.domains.team.domain;

import lombok.Getter;

public class TeamSimpleInfo {


	@Getter
	public static class TeamName {

		private final Long teamId;
		private final String teamName;

		public TeamName(Long teamId, String teamName) {
			this.teamId = teamId;
			this.teamName = teamName;
		}

	}

	@Getter
	public static class TeamNameAndLogo {

		private final Long teamId;
		private final String teamName;
		private final String teamLogo;

		public TeamNameAndLogo(Long teamId, String teamName, String teamLogo) {
			this.teamId = teamId;
			this.teamName = teamName;
			this.teamLogo = teamLogo;
		}

	}

}
