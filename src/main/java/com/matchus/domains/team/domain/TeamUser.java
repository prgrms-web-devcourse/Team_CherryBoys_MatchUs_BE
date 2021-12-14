package com.matchus.domains.team.domain;

import com.matchus.domains.user.domain.User;
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
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE team_users SET is_disaffiliated = true WHERE id=?")
@Where(clause = "is_disaffiliated = false")
@Entity
@Table(name = "team_users")
public class TeamUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "team_id",
		referencedColumnName = "id"
	)
	private Team team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "user_id",
		referencedColumnName = "id"
	)
	private User user;

	@Enumerated(EnumType.STRING)
	private Grade grade;

	@Column(nullable = false, columnDefinition = "BOOLEAN default false")
	private boolean isDisaffiliated = false;

	@Builder
	private TeamUser(Long id, Team team, User user, Grade grade) {
		this.id = id;
		setTeam(team);
		setUser(user);
		this.grade = grade;
	}

	public void setTeam(Team team) {
		if (this.team != null) {
			this.team.getTeamUsers().remove(this);
		}

		this.team = team;
		team.getTeamUsers().add(this);
	}

	public void setUser(User user) {
		if (this.user != null) {
			this.user.getTeamUsers().remove(this);
		}

		this.user = user;
		user.getTeamUsers().add(this);
	}

	public void changeGrade(Grade grade) {
		this.grade = grade;
	}
}
