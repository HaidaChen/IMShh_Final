package com.douniu.imshh.sys.service;

import java.util.List;

import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.RoleAuthority;

public interface IRoleService {
	List<Role> query();
	List<Role> queryByUser(String userId);
	Role findById(String id);
	void add(Role role);
	void addAuthorityRelation(List<RoleAuthority> roleAuthorities);
	void deleteAuthorityRelation(String roleId);
	void delete(String id);
}
