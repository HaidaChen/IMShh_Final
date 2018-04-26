package com.douniu.imshh.sys.dao;

import java.util.List;

import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.sys.domain.UserRole;

public interface IUserDao {
	List<User> query(User user);
	int count(User user);
	User findById(String id);
	User findByNmPwd(User user);
	void insert(User user);
	void update(User user);
	void delete(String id);
	void deleteRoleRelation(String userId);
	void addRoleRelation(List<UserRole> userRole);
}
