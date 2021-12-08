package com.matchus.domains.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Entity
@Table(name = "groups")
@Setter
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "group")
	private List<GroupPermission> permissions = new ArrayList<>();

	public Group() {

	}

	public Group(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public List<GrantedAuthority> getAuthorities() {
		return permissions
			.stream()
			.map(gp -> new SimpleGrantedAuthority(gp
													  .getPermission()
													  .getName()))
			.collect(Collectors.toList());
	}

}
