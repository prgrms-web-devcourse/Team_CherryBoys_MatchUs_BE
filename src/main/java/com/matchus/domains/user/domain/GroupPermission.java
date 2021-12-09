package com.matchus.domains.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@Table(name = "group_permissions")
@AllArgsConstructor
public class GroupPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	@ManyToOne
	@JoinColumn(name = "permission_id")
	private Permission permission;


	public GroupPermission() {

	}
}
