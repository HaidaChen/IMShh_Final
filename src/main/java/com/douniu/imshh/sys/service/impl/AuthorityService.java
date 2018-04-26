package com.douniu.imshh.sys.service.impl;

import java.util.List;

import com.douniu.imshh.sys.dao.IAuthorityDao;
import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.service.IAuthorityService;

public class AuthorityService implements IAuthorityService{

	private IAuthorityDao dao;
	
	@Override
	public List<Authority> query() {
		return dao.query();
	}

	@Override
	public List<Authority> queryByRole(String roleId) {
		return dao.queryByRole(roleId);
	}
	
	@Override
	public List<Authority> queryByUser(String userId) {
		return dao.queryByUser(userId);
	}

	@Override
	public Authority findById(String id) {
		return dao.findById(id);
	}

	public void setDao(IAuthorityDao dao) {
		this.dao = dao;
	}

}
