package com.matchus.domains.hire.domain;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.domains.location.domain.City;
import com.matchus.domains.location.domain.Ground;
import com.matchus.domains.location.domain.Region;
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
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Entity
@Table(name = "hire_posts")
public class HirePost extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String position;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "city_id",
		referencedColumnName = "id"
	)
	private City city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "region_id",
		referencedColumnName = "id"
	)
	private Region region;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "ground_id",
		referencedColumnName = "id"
	)
	private Ground ground;

	@Embedded
	private Period period;

	@Enumerated(EnumType.STRING)
	private AgeGroup ageGroup;

	@Column
	private String detail;

	@Column(nullable = false, columnDefinition = "INT default 1")
	private int hirePlayerNumber = 1;

	@Column(nullable = false, columnDefinition = "BOOLEAN default false")
	private boolean isDeleted = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "team_id",
		referencedColumnName = "id"
	)
	private Team team;

	@Builder
	private HirePost(
		Long id,
		String position,
		City city,
		Region region,
		Ground ground,
		Period period,
		AgeGroup ageGroup,
		String detail,
		int hirePlayerNumber
	) {
		this.id = id;
		this.position = position;
		this.city = city;
		this.region = region;
		this.ground = ground;
		this.period = period;
		this.ageGroup = ageGroup;
		this.detail = detail;
		this.hirePlayerNumber = hirePlayerNumber;
	}

	public void setTeam(Team team) {
		if (this.team != null) {
			this.team.getHirePosts().remove(this);
		}

		this.team = team;
		team.getHirePosts().add(this);
	}

	public void updateHirePost(
		String position,
		City city,
		Region region,
		Ground ground,
		Period period,
		AgeGroup ageGroup,
		String detail,
		int hirePlayerNumber
	) {
		this.position = position;
		this.city = city;
		this.region = region;
		this.ground = ground;
		this.period = period;
		this.ageGroup = ageGroup;
		this.detail = detail;
		this.hirePlayerNumber = hirePlayerNumber;
	}
}
