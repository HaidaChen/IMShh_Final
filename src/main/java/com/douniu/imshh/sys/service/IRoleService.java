package com.douniu.imshh.sys.service;

import java.util.List;

import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.SystemFilter;

public interface IRoleService {
	List<Role> query(SystemFilter filter);
	List<Role> queryByUser(String userId);
	Role findById(String id);
	void newRole(Role role, String authorityIds);
	void updateRole(Role role, String authorityIds);
	void delete(String id);
}
