package com.douniu.imshh.finance.order.domain;

public class OrderDetail {
	private String id;
	private String orderId;  /*订单编号*/
	private String pdtNo; /*货号*/
	private String pdtId;    /*产品编号*/
	private String pdtName;  /*产品名称*/
	private String content;  /*产品含量*/
	private int quantity;    /*产品数量*/
	private float priceRMB;  /*人民币单价*/
	private float priceDollar; /*美元单价*/
	private float totlemnt;    /*合计*/
	private float progress;  /*交付进度*/
	
	private String remark;
	private int status = 1;
	
	public OrderDetail(){}
	
	
	public OrderDetail(String id, String orderId, String pdtNo, String pdtName, String content, int quantity, float priceRMB, float priceDollar,
			float totlemnt, String remark) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.pdtNo = pdtNo;
		this.pdtName = pdtName;
		this.content = content;
		this.quantity = quantity;
		this.priceRMB = priceRMB;
		this.priceDollar = priceDollar;
		this.totlemnt = totlemnt;
		this.remark = remark;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}	
	public String getPdtNo() {
		return pdtNo;
	}
	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}
	public String getPdtId() {
		return pdtId;
	}
	public void setPdtId(String pdtId) {
		this.pdtId = pdtId;
	}
	public String getPdtName() {
		return pdtName;
	}
	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPriceRMB() {
		return priceRMB;
	}
	public void setPriceRMB(float priceRMB) {
		this.priceRMB = priceRMB;
	}
	public float getPriceDollar() {
		return priceDollar;
	}
	public void setPriceDollar(float priceDollar) {
		this.priceDollar = priceDollar;
	}
	
	public float getTotlemnt() {
		return totlemnt;
	}
	public void setTotlemnt(float totlemnt) {
		this.totlemnt = totlemnt;
	}
	public float getProgress() {
		return progress;
	}
	public void setProgress(float progress) {
		this.progress = progress;
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
	
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", orderId=" + orderId + ", pdtNo=" + pdtNo + ", pdtId=" + pdtId + ", pdtName="
				+ pdtName + ", content=" + content + ", quantity=" + quantity + ", priceRMB=" + priceRMB
				+ ", priceDollar=" + priceDollar + ", totlemnt=" + totlemnt + ", progress=" + progress + ", remark="
				+ remark + ", status=" + status + "]";
	}


}
