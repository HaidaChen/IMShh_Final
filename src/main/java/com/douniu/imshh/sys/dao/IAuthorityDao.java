package com.douniu.imshh.sys.dao;

import java.util.List;

import com.douniu.imshh.sys.domain.Authority;

public interface IAuthorityDao {
	List<Authority> query();
	List<Authority> queryByRole(String roleId);
	List<Authority> queryByUser(String userId);
	Authority findById(String id);
}
