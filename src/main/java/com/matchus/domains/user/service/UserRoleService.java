package com.matchus.domains.user.service;

import com.matchus.domains.user.domain.Role;
import com.matchus.domains.user.domain.RoleName;
import com.matchus.domains.user.domain.User;
import com.matchus.domains.user.domain.UserRole;
import com.matchus.domains.user.exception.RoleNotFoundException;
import com.matchus.domains.user.repository.RoleRespository;
import com.matchus.domains.user.repository.UserRoleRepository;
import com.matchus.global.error.ErrorCode;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

	private final RoleRespository roleRespository;
	private final UserRoleRepository userRoleRepository;

	public UserRoleService(
		RoleRespository roleRespository,
		UserRoleRepository userRoleRepository
	) {
		this.roleRespository = roleRespository;
		this.userRoleRepository = userRoleRepository;
	}

	public UserRole createUserRole(User user) {
		Role userRole = roleRespository
			.findByName(RoleName.USER)
			.orElseThrow(() -> new RoleNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

		return UserRole
			.builder()
			.role(userRole)
			.user(user)
			.build();

	}
}
