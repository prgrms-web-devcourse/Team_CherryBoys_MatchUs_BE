package com.matchus.domains.hire.domain;

import com.matchus.domains.common.Address;
import com.matchus.domains.common.AgeGroup;
import com.matchus.domains.common.Period;
import com.matchus.global.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
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

	@Column(nullable = false, length = 100)
	private String title;

	@Column(length = 50)
	private String position;

	@Embedded
	private Address address;

	@Embedded
	private Period period;

	@Enumerated(EnumType.STRING)
	private AgeGroup ageGroup;

	@Column
	private String detail;

	@Column(nullable = false, columnDefinition = "INT default 1")
	private int hirePlayerNumber;

	@Column(nullable = false, columnDefinition = "BOOLEAN default false")
	private boolean isDeleted;
}
