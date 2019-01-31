package com.douniu.imshh.sys.dao;

import java.util.List;

import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.domain.Menu;

public interface IAuthorityDao {
	List<Authority> query();
	List<Authority> queryByRole(String roleId);
	List<Authority> queryByUser(String userId);
	List<Menu> queryMenuByUser(String userId);
	List<Menu> getAllMenu();
	Menu findMenuById(String menuId);
	Authority findById(String id);
}
