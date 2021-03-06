package com.douniu.imshh.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.sys.dao.IUserDao;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.SystemFilter;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.domain.UserRole;
import com.douniu.imshh.sys.service.IUserService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class UserService implements IUserService{

	private IUserDao dao;
	
	
	@Override
	public PageResult getPageResult(SystemFilter filter) {
		PageResult pr = new PageResult();
		SystemFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"username"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	
	@Override
	public List<User> queryWithInvalid(SystemFilter filter) {
		SystemFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"username"});
		return dao.getPageResult(condition);
	}


	@Override
	public User findByNmPwd(User user) {
		return dao.findByNmPwd(user);
	}

	@Override
	public User findById(String id) {
		return dao.findById(id);
	}

	@Override
	public void update(User user) {
		dao.update(user);
		
		if (user.getRoles() != null){
			this.deleteRoleRelation(user.getId());
			List<UserRole> uRoles = new ArrayList<>();
			for(Role role : user.getRoles()){
				UserRole uRole = new UserRole(user.getId(), role.getId());
				uRoles.add(uRole);
			}
			this.addRoleRelation(uRoles);
		}
	}
	
	@Override
	public void add(User user) {
		dao.insert(user);
		
		if (user.getRoles() != null){
			List<UserRole> uRoles = new ArrayList<>();
			for(Role role : user.getRoles()){
				UserRole uRole = new UserRole(user.getId(), role.getId());
				uRoles.add(uRole);
			}
			this.addRoleRelation(uRoles);
		}
	}

	@Override
	public void remove(String id) {
		dao.delete(id);
		this.deleteRoleRelation(id);
	}

	@Override
	public boolean existUserName(String userName) {
		return dao.findByName(userName) != null;
	}
	
	@Override
	public boolean verify(User user) {
		return dao.findByNmPwd(user) != null;
	}

	@Override
	public void addRoleRelation(List<UserRole> userRoles) {
		dao.addRoleRelation(userRoles);
	}

	@Override
	public void deleteRoleRelation(String userId) {
		dao.deleteRoleRelation(userId);
	}

	
	@Override
	public void setHomePage(String id, String homePage) {
		dao.setHomePage(id, homePage);
	}


	public void setDao(IUserDao dao) {
		this.dao = dao;
	}

}
