package com.douniu.imshh.sys.service.impl;

import java.util.List;

import com.douniu.imshh.sys.dao.IMenuDao;
import com.douniu.imshh.sys.domain.Menu;
import com.douniu.imshh.sys.service.IMenuService;

public class MenuService implements IMenuService{
	
	private IMenuDao dao;
	
	@Override
	public List<Menu> query() {
		return dao.query();
	}

	public void setDao(IMenuDao dao) {
		this.dao = dao;
	}	
}
