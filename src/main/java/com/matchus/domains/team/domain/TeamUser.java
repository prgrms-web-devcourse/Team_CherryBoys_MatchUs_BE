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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET is_disaffiliated = true WHERE id=?")
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
	private boolean isDisaffiliated;
}
