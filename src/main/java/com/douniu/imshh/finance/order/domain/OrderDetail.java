package com.douniu.imshh.finance.order.domain;

public class OrderDetail {
	private String id;
	private String orderIdentify;  /*订单编号*/
	private String pdtNo; /*货号*/
	private String pdtName;  /*产品名称*/
	private String content;  /*产品含量*/
	private int quantity;    /*产品数量*/
	private Float priceRMB;  /*人民币单价*/
	private Float priceDollar; /*美元单价*/
	private Float totlmentRMB;    /*合计*/
	private Float totlmentDollar;    /*合计*/
	private int inStorageQuantity;  /*生产数量*/
	private int deliverQuantity;  /*交付数量*/
	
	private String remark;
	private int status = 1;
	
	public OrderDetail(){}
	
	
	public OrderDetail(String id, String orderIdentify, String pdtNo, String pdtName, String content, int quantity, Float priceRMB, Float priceDollar,
			Float totlmentRMB, Float totlmentDollar, String remark) {
		super();
		this.id = id;
		this.orderIdentify = orderIdentify;
		this.pdtNo = pdtNo;
		this.pdtName = pdtName;
		this.content = content;
		this.quantity = quantity;
		this.priceRMB = priceRMB;
		this.priceDollar = priceDollar;
		this.totlmentRMB = totlmentRMB;
		this.totlmentDollar = totlmentDollar;
		this.remark = remark;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
		
	public String getOrderIdentify() {
		return orderIdentify;
	}


	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
	}


	public String getPdtNo() {
		return pdtNo;
	}
	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
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
	public Float getPriceRMB() {
		return priceRMB;
	}
	public void setPriceRMB(Float priceRMB) {
		this.priceRMB = priceRMB;
	}
	public Float getPriceDollar() {
		return priceDollar;
	}
	public void setPriceDollar(Float priceDollar) {
		this.priceDollar = priceDollar;
	}
	
	public Float getTotlmentRMB() {
		return totlmentRMB;
	}


	public void setTotlmentRMB(Float totlmentRMB) {
		this.totlmentRMB = totlmentRMB;
	}


	public Float getTotlmentDollar() {
		return totlmentDollar;
	}


	public void setTotlmentDollar(Float totlmentDollar) {
		this.totlmentDollar = totlmentDollar;
	}


	public int getInStorageQuantity() {
		return inStorageQuantity;
	}


	public void setInStorageQuantity(int inStorageQuantity) {
		this.inStorageQuantity = inStorageQuantity;
	}


	public int getDeliverQuantity() {
		return deliverQuantity;
	}


	public void setDeliverQuantity(int deliverQuantity) {
		this.deliverQuantity = deliverQuantity;
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
		return "OrderDetail [id=" + id + ", orderIdentify=" + orderIdentify + ", pdtNo=" + pdtNo + ", pdtName="
				+ pdtName + ", content=" + content + ", quantity=" + quantity + ", priceRMB=" + priceRMB
				+ ", priceDollar=" + priceDollar + ", totlmentRMB=" + totlmentRMB + ", totlmentDollar=" + totlmentDollar
				+ ", inStorageQuantity=" + inStorageQuantity + ", deliverQuantity=" + deliverQuantity + ", remark="
				+ remark + ", status=" + status + "]";
	}
	
}
