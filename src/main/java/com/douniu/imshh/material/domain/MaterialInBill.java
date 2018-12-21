package com.douniu.imshh.material.domain;

import com.douniu.imshh.busdata.supplier.domain.Supplier;

public class MaterialInBill extends MaterialBill{
	private Supplier supplier;   /*供应商*/
	private String manager;      /*主管*/
	private String accountant;   /*会计*/
	private String custodian;    /*保管*/
	private String acceptor;     /*验收*/
	private String handover;     /*交库*/
	private int billStatus = 0;      /*单据状态：0：待入账；1：已入账*/
	
	private String remark;
	private int status = 1;
	
	
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getAccountant() {
		return accountant;
	}
	public void setAccountant(String accountant) {
		this.accountant = accountant;
	}
	public String getCustodian() {
		return custodian;
	}
	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}
	public String getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}
	public String getHandover() {
		return handover;
	}
	public void setHandover(String handover) {
		this.handover = handover;
	}	
	
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
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
		return "MaterialInBill [supplier=" + supplier + ", manager=" + manager + ", accountant=" + accountant
				+ ", custodian=" + custodian + ", acceptor=" + acceptor + ", handover=" + handover + ", billStatus="
				+ billStatus + ", remark=" + remark + ", status=" + status + "]";
	}
	
	
	/*private String id;
	private Date inDate;
	private String materialId;          原材料ID
	private String materialName;        原材料名称	
	private String specification;      规格
	private String unit;                计量单位
	private String supplierId;          供应商ID
	private String supplierName;        供应商
	private float amount;
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
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
	*/
}
