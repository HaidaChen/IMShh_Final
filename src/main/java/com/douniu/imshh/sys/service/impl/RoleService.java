package com.douniu.imshh.sys.service.impl;

import java.util.List;

import com.douniu.imshh.sys.dao.IRoleDao;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.RoleAuthority;
import com.douniu.imshh.sys.service.IRoleService;

public class RoleService implements IRoleService{
	private IRoleDao dao;
	
	@Override
	public List<Role> query() {
		return dao.query();
	}

	@Override
	public List<Role> queryByUser(String userId) {
		return dao.queryByUser(userId);
	}

	@Override
	public Role findById(String id) {
		return dao.findById(id);
	}

	@Override
	public void deleteAuthorityRelation(String roleId) {
		dao.deleteAuthorityRelation(roleId);
	}
	
	@Override
	public void addAuthorityRelation(List<RoleAuthority> roleAuthorities) {
		dao.addAuthorityRelation(roleAuthorities);
	}
	
	@Override
	public void add(Role role) {
		dao.insert(role);
	}	

	@Override
	public void delete(String id) {
		dao.delete(id);
		deleteAuthorityRelation(id);
	}	
	
	public void setDao(IRoleDao dao) {
		this.dao = dao;
	}
		
}
