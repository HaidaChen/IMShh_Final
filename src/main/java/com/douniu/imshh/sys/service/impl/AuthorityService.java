package com.douniu.imshh.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.sys.dao.IAuthorityDao;
import com.douniu.imshh.sys.domain.Authority;
import com.douniu.imshh.sys.domain.Menu;
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

	@Override
	public List<Menu> queryMenuTreeByUser(String userId) {
		List<Menu> menus = dao.queryMenuByUser(userId);
		Menu root = new Menu();
		root.setId("0");
		menuList2Tree(menus, root);
		return root.getSubmenu();
	}
	
	@Override
	public List<Menu> queryMenuByUser(String userId) {
		List<Menu> menus = dao.queryMenuByUser(userId);
		List<Menu> _menus = new ArrayList<>();
		for (Menu menu : menus){
			if (!StringUtils.isEmpty(menu.getUrl())){
				_menus.add(menu);
			}
		}
		return _menus;
	}
	
	@Override
	public List<Menu> getAllMenu() {
		List<Menu> menus = dao.getAllMenu();
		Menu root = new Menu();
		root.setId("0");
		menuList2Tree(menus, root);
		return root.getSubmenu();
	}

	@Override
	public Menu findMenuById(String menuId) {
		return dao.findMenuById(menuId);
	}

	private void menuList2Tree(List<Menu> src, Menu parent){
		List<Menu> submenus = new ArrayList<Menu>();
		for(Menu menu : src){
			if (menu.getParentId().equals(parent.getId())){
				submenus.add(menu);
				menuList2Tree(src, menu);
			}
		}
		parent.setSubmenu(submenus);
	}
	
	public void setDao(IAuthorityDao dao) {
		this.dao = dao;
	}

}
