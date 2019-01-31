package com.douniu.imshh.sys.domain;

import com.douniu.imshh.common.BaseQO;

public class SystemFilter extends BaseQO{
	private String userId;
	private String username;
	private String password;
	private String roleName;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "SystemFilter [userId=" + userId + ", username=" + username + ", password=" + password + ", roleName="
				+ roleName + "]";
	}
}
