package com.douniu.imshh.sys.dao;

import java.util.List;

import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.RoleAuthority;
import com.douniu.imshh.sys.domain.SystemFilter;

public interface IRoleDao {
	List<Role> query(SystemFilter filter);
	List<Role> queryByUser(String userId);
	Role findById(String id);
	void insert(Role role);
	void update(Role role);
	void deleteAuthorityRelation(String roleId);
	void addAuthorityRelation(List<RoleAuthority> roleAuthorities);
	void delete(String id);
}
