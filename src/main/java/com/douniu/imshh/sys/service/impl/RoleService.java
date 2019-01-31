package com.douniu.imshh.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.sys.dao.IRoleDao;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.RoleAuthority;
import com.douniu.imshh.sys.domain.SystemFilter;
import com.douniu.imshh.sys.service.IRoleService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class RoleService implements IRoleService{
	private IRoleDao dao;
	
	@Override
	public List<Role> query(SystemFilter filter) {
		SystemFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"roleName"});
		return dao.query(condition);
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
	public void newRole(Role role, String authorityIds) {
		IDInjector.injector(role);
		List<RoleAuthority> roleAuthorities = new ArrayList<>();
		String[] authorityIdArr = authorityIds.split(",");		
		for (String authorityId : authorityIdArr){
			if (!StringUtils.isEmpty(authorityId)){
				RoleAuthority roleAuthority = new RoleAuthority(role.getId(), authorityId);
				roleAuthorities.add(roleAuthority);
			}
		}
		dao.addAuthorityRelation(roleAuthorities);
		dao.insert(role);
	}

	@Override
	public void updateRole(Role role, String authorityIds) {
		dao.deleteAuthorityRelation(role.getId());
		List<RoleAuthority> roleAuthorities = new ArrayList<>();
		String[] authorityIdArr = authorityIds.split(",");		
		for (String authorityId : authorityIdArr){
			RoleAuthority roleAuthority = new RoleAuthority(role.getId(), authorityId);
			roleAuthorities.add(roleAuthority);
		}
		dao.addAuthorityRelation(roleAuthorities);
		dao.update(role);
	}

	@Override
	public void delete(String id) {
		dao.delete(id);
		dao.deleteAuthorityRelation(id);
	}	
	
	public void setDao(IRoleDao dao) {
		this.dao = dao;
	}
		
}
