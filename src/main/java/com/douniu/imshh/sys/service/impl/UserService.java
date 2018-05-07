package com.douniu.imshh.sys.service.impl;

import java.util.List;

import com.douniu.imshh.sys.dao.IUserDao;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.domain.UserRole;
import com.douniu.imshh.sys.service.IUserService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class UserService implements IUserService{

	private IUserDao dao;
	
	@Override
	public List<User> query(User user) {
		User condition = LikeFlagUtil.appendLikeFlag(user, new String[]{"userName", "fullName", "email", "weichat"});
		return dao.query(condition);
	}	

	@Override
	public User findByNmPwd(User user) {
		return dao.findByNmPwd(user);
	}



	@Override
	public int count(User user) {
		User condition = LikeFlagUtil.appendLikeFlag(user, new String[]{"userName", "fullName", "email", "weichat"});
		return dao.count(condition);
	}

	@Override
	public User findById(String id) {
		return dao.findById(id);
	}

	@Override
	public void update(User user) {
		dao.update(user);
	}
	
	@Override
	public void updateProfile(User user) {
		dao.updateProfile(user);
	}

	@Override
	public void add(User user) {
		dao.insert(user);
	}

	@Override
	public void remove(String id) {
		dao.delete(id);
	}

	@Override
	public boolean existUserName(String userName) {
		User user = new User();
		user.setUserName(userName);
		return dao.findByNmPwd(user) != null;
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

	public void setDao(IUserDao dao) {
		this.dao = dao;
	}

	

}
