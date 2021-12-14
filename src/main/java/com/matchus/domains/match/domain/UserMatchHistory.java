package com.matchus.domains.match.domain;

import com.matchus.domains.user.domain.User;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_match_histories")
public class UserMatchHistory {

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
		name = "match_id",
		referencedColumnName = "id"
	)
	private Match match;

	@Builder
	public UserMatchHistory(Long id, User user, Match match) {
		this.id = id;
		setUser(user);
		this.match = match;
	}

	public void setUser(User user) {
		if (this.user != null) {
			this.user
				.getUserMatchHistorys()
				.remove(this);
		}

		this.user = user;
		user
			.getUserMatchHistorys()
			.add(this);
	}

}
