package com.matchus.domains.match.domain;

import com.matchus.domains.team.domain.Team;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "team_waitings")
public class TeamWaiting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "match_id",
		referencedColumnName = "id"
	)
	private Match match;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "team_id",
		referencedColumnName = "id"
	)
	private Team team;

	@Enumerated(EnumType.STRING)
	private WaitingType type;

	@Builder
	public TeamWaiting(
		Long id,
		Match match,
		Team team,
		WaitingType type
	) {
		this.id = id;
		this.match = match;
		this.team = team;
		this.type = type;
	}

	public void changeWaitingType(WaitingType type) {
		this.type = type;
	}

}
