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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member_waitings")
public class MemberWaiting {

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
		name = "team_waiting_id",
		referencedColumnName = "id"
	)
	private TeamWaiting teamWaiting;
}
