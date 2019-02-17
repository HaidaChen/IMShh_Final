package com.douniu.imshh.finance.domain;

import com.douniu.imshh.product.domain.Product;

/**
 * 会计科目，科目为树形结构。允许增删改
 * @author Administrator
 *
 */
public class Subject {
	private String id;
	/*科目编号，唯一*/
	private String code;
	/*科目名称*/
	private String name;
	/*科目全名：编号 名称以及父科目的全名*/
	private String fullName;
	/*科目分类（固定），01：资产类；02：负债累；03：共同类；04：所有者权益；05：成本：06：损益*/
	private String category;
	private Subject parent;
	/*初始余额*/
	private float initBalance;
	private int privateSubject;
	private String remark;
	private int status = 1;
	
	public Subject(){super();}
	public Subject(String id){this.id = id;}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}	
	public Subject getParent() {
		return parent;
	}
	public void setParent(Subject parent) {
		this.parent = parent;
	}
	public float getInitBalance() {
		return initBalance;
	}
	public void setInitBalance(float initBalance) {
		this.initBalance = initBalance;
	}
	public int getPrivateSubject() {
		return privateSubject;
	}
	public void setPrivateSubject(int privateSubject) {
		this.privateSubject = privateSubject;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Subject [id=" + id + ", code=" + code + ", name=" + name + ", fullName=" + fullName + ", category="
				+ category + ", parent=" + parent + ", initBalance=" + initBalance + ", privateSubject="
				+ privateSubject + ", remark=" + remark + ", status=" + status + "]";
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return this.code.equals(subject.code);
    }
}
