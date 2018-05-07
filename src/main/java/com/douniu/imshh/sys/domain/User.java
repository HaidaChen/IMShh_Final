package com.douniu.imshh.sys.domain;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.common.BaseQO;

public class User extends BaseQO{
	private String id;
	private String userName;
	private String password;	
	private String fullName;
	
	private Date birthday;
	private int gender;
	private String workNo;
	private String dept;
	private String position;
	
	private String phone;
	private String email;
	private String QQ;
	private String weichat;
	private String head;
	
	private List<Role> roles;
	
	private String remark;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWeichat() {
		return weichat;
	}

	public void setWeichat(String weichat) {
		this.weichat = weichat;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", fullName=" + fullName
				+ ", birthday=" + birthday + ", gender=" + gender + ", workNo=" + workNo + ", dept=" + dept
				+ ", position=" + position + ", phone=" + phone + ", email=" + email + ", QQ=" + QQ + ", weichat="
				+ weichat + ", head=" + head + ", roles=" + roles + ", status=" + status + "]";
	}	
}
