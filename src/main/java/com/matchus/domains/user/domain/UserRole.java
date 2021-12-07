package com.matchus.domains.user.domain;

import javax.persistence.Entity;
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

@Entity
@Table(name = "user_roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "user_id",
		referencedColumnName = "id"
	)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "role_id",
		referencedColumnName = "id"
	)
	private Role role;

	@Builder
	public UserRole(Long id, User user, Role role) {
		this.id = id;
		this.role = role;
		setUser(user);
	}

	public void setUser(User user) {
		if (this.user != null) {
			this.user
				.getUserRoles()
				.remove(this);
		}

		this.user = user;
		user
			.getUserRoles()
			.add(this);
	}

}
