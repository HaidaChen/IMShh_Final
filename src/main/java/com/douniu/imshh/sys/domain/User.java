package com.douniu.imshh.sys.domain;

import java.util.List;

import com.douniu.imshh.common.BaseQO;

public class User extends BaseQO{
	private String id;
	private String userName;
	private String password;	
	private String fullName;
	
	private String email;
	private String weichat;
	
	private List<Role> roles;
	
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeichat() {
		return weichat;
	}

	public void setWeichat(String weichat) {
		this.weichat = weichat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
