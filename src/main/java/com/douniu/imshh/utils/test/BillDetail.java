package com.douniu.imshh.utils.test;
public class BillDetail{
		private Product product;
		private int quantity;
		private String remark;
		public BillDetail(Product product, int quantity, String remark) {
			super();
			this.product = product;
			this.quantity = quantity;
			this.remark = remark;
		}
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
	}