package com.matchus.domains.match.domain;

import com.matchus.domains.common.Address;
import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.team.domain.Team;
import com.matchus.global.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET is_cancelled = true WHERE id=?")
@Where(clause = "is_cancelled = false")
@Entity
@Table(name = "matches")
public class Match extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "register_team_id",
		referencedColumnName = "id"
	)
	private Team homeTeam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "apply_team_id",
		referencedColumnName = "id"
	)
	private Team awayTeam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "sport_id",
		referencedColumnName = "id"
	)
	private Sports sport;

	@Embedded
	private Address address;

	@Embedded
	private Period period;

	@Enumerated(EnumType.STRING)
	private AgeGroup ageGroup;

	@Column(nullable = false)
	private int cost;

	@Column
	private String detail;

	@Enumerated(EnumType.STRING)
	private MatchStatus status;

	@Column(nullable = false, columnDefinition = "BOOLEAN default false")
	private boolean isCancelled = false;

	@Builder
	public Match(
		Long id,
		Team homeTeam,
		Team awayTeam,
		Sports sport,
		Address address,
		Period period,
		AgeGroup ageGroup,
		int cost,
		String detail,
		MatchStatus status,
		boolean isCancelled
	) {
		this.id = id;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.sport = sport;
		this.address = address;
		this.period = period;
		this.ageGroup = ageGroup;
		this.cost = cost;
		this.detail = detail;
		this.status = status;
		this.isCancelled = isCancelled;
	}

}
