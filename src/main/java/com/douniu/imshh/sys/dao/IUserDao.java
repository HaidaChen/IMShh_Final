package com.douniu.imshh.sys.dao;

import java.util.List;

import com.douniu.imshh.sys.domain.SystemFilter;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.domain.UserRole;

public interface IUserDao {
	List<User> getPageResult(SystemFilter filter);
	int count(SystemFilter filter);
	List<User> queryWithInvalid(SystemFilter filter);
	/*List<User> query(User user);
	int count(User user);*/
	User findById(String id);
	User findByNmPwd(User user);
	User findByName(String userName);
	void insert(User user);
	void update(User user);
	void updateProfile(User user);
	void delete(String id);
	void deleteRoleRelation(String userId);
	void addRoleRelation(List<UserRole> userRole);
}
