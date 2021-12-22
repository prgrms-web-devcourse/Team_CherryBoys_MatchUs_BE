package com.matchus.domains.user.domain;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.sports.domain.Sports;
import com.matchus.domains.team.domain.TeamUser;
import com.matchus.global.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET is_disaffiliated = true WHERE id=?")
@Where(clause = "is_disaffiliated = false")
@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "sport_id",
		referencedColumnName = "id"
	)
	private Sports sport;

	@Column(nullable = false, length = 320, unique = true)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, length = 20, unique = true)
	private String nickname;

	@Column(columnDefinition = "TEXT")
	private String bio;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grouping_id")
	private Grouping grouping;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AgeGroup ageGroup;

	@Column(nullable = false, columnDefinition = "DECIMAL(4,1) default '36.5'", precision = 4, scale = 1)
	private BigDecimal mannerTemperature = new BigDecimal("36.5");

	@Column(nullable = false, columnDefinition = "BOOLEAN default false")
	private boolean isDisaffiliated = false;

	@OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<TeamUser> teamUsers = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<UserMatchHistory> userMatchHistorys = new ArrayList<>();

	@Builder
	private User(
		Long id,
		Sports sport,
		String email,
		String name,
		String password,
		Grouping grouping,
		String nickname,
		String bio,
		Gender gender,
		AgeGroup ageGroup
	) {
		this.id = id;
		this.sport = sport;
		this.email = email;
		this.grouping = grouping;
		this.name = name;
		this.password = password;
		this.nickname = nickname;
		this.bio = bio;
		this.gender = gender;
		this.ageGroup = ageGroup;
	}

	public List<UserMatchHistory> getAllMatches() {
		return this.userMatchHistorys
			.stream()
			.sorted(Comparator
						.comparing(
							(UserMatchHistory userMatchHistory) -> userMatchHistory
								.getMatch()
								.getPeriod()
								.getDate()
						)
						.reversed())
			.collect(Collectors.toList());
	}

	public int getMatchCount() {
		return this.userMatchHistorys.size();
	}

	public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
		if (!passwordEncoder.matches(credentials, password)) {
			throw new IllegalArgumentException("Bad credential");
		}
	}

	public void deactivateUser() {
		this.isDisaffiliated = true;
	}

	public void changeInfo(
		String nickname,
		String bio,
		AgeGroup ageGroup,
		Sports sport
	) {
		this.nickname = nickname;
		this.bio = bio;
		this.ageGroup = ageGroup;
		this.sport = sport;
	}

	public void updateMannerTemperature(BigDecimal value) {
		this.mannerTemperature = value;
	}
}
