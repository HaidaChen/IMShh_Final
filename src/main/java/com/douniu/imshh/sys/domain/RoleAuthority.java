package com.douniu.imshh.sys.domain;

public class RoleAuthority {
	private String roleId;
	private String authorityId;
	
	public RoleAuthority(){super();}	
	
	public RoleAuthority(String roleId, String authorityId) {
		super();
		this.roleId = roleId;
		this.authorityId = authorityId;
	}

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}	
}
