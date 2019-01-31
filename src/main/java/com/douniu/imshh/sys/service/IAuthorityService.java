package com.douniu.imshh.sys.service;

import java.util.List;

import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.domain.Menu;

public interface IAuthorityService {
	List<Authority> query();
	List<Authority> queryByRole(String roleId);
	List<Authority> queryByUser(String userId);
	List<Menu> queryMenuByUser(String userId);
	List<Menu> queryMenuTreeByUser(String userId);
	Menu findMenuById(String menuId);
	List<Menu> getAllMenu();
	Authority findById(String id);	
}
