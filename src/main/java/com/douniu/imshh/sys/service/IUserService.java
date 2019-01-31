package com.douniu.imshh.sys.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.sys.domain.SystemFilter;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.domain.UserRole;

public interface IUserService{
	PageResult getPageResult(SystemFilter filter);
	List<User> queryWithInvalid(SystemFilter filter);
	User findByNmPwd(User user);
	//int count(User user);
	User findById(String id);
	void update(User user);
	
	void add(User user);
	void remove(String id);
	boolean verify(User user);
	boolean existUserName(String userName);
	
	void addRoleRelation(List<UserRole> userRoles);
	void deleteRoleRelation(String userId);
	
	void setHomePage(String id, String homePage);
}
