package com.matchus.domains.user.domain;

import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Sports;
import com.matchus.global.domain.BaseEntity;
import java.math.BigDecimal;
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
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AgeGroup ageGroup;

	@Column(nullable = false, columnDefinition = "DECIMAL(4,1) default '36.5'", precision = 4, scale = 1)
	private BigDecimal mannerTemperature;

	@Column(nullable = false, columnDefinition = "BOOLEAN default false")
	private boolean isDisaffiliated;
}