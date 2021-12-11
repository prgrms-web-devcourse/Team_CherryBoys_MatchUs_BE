package com.matchus.domains.team.domain;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.hire.domain.HirePost;
import com.matchus.domains.match.domain.Match;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.tag.domain.TeamTag;
import com.matchus.global.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "teams")
public class Team extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "sport_id",
		referencedColumnName = "id"
	)
	private Sports sport;

	@Column(nullable = false, length = 20, unique = true)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String bio;

	@Column(columnDefinition = "TEXT")
	private String logo;

	@Enumerated(EnumType.STRING)
	private AgeGroup ageGroup;

	@Column(nullable = false, columnDefinition = "DECIMAL(4,1) default '36.5'", precision = 4, scale = 1)
	private BigDecimal mannerTemperature = new BigDecimal("36.5");

	@Column(nullable = false, columnDefinition = "BOOLEAN default false")
	private boolean isDeleted = false;

	@OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<TeamUser> teamUsers = new ArrayList<>();

	@OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<HirePost> hirePosts = new ArrayList<>();

	@OneToMany(mappedBy = "homeTeam", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Match> homeMatches = new ArrayList<>();

	@OneToMany(mappedBy = "awayTeam", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Match> awayMatches = new ArrayList<>();

	@OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<TeamTag> teamTags = new ArrayList<>();

	@Builder
	private Team(Long id, Sports sport, String name, String bio, String logo, AgeGroup ageGroup) {
		this.id = id;
		this.sport = sport;
		this.name = name;
		this.bio = bio;
		this.logo = logo;
		this.ageGroup = ageGroup;
	}

	public void changeInfo(String logo, String bio, AgeGroup ageGroup) {
		this.logo = logo;
		this.bio = bio;
		this.ageGroup = ageGroup;
	}

	public int getMatchCount() {
		return this.getHomeMatches().size() + this.getAwayMatches().size();
	}

	public List<Match> getAllMatches() {
		return Stream
			.concat(this.getHomeMatches().stream(), this.getAwayMatches().stream())
			.sorted(Comparator.comparing(Match::getId).reversed())
			.collect(Collectors.toList());
	}
}
