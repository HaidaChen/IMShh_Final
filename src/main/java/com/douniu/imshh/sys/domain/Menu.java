package com.douniu.imshh.sys.domain;

import java.util.List;

public class Menu {
	private String id;
	private String name;
	private String icon;
	private String url;
	private String parentId;
	private List<Menu> submenu;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List<Menu> getSubmenu() {
		return submenu;
	}
	public void setSubmenu(List<Menu> submenu) {
		this.submenu = submenu;
	}
	
}
