package com.douniu.imshh.utils.test;

import java.util.Date;
import java.util.List;

public class Bill{
		private String number;
		private Date billDate;
		private String billReason;
		private List<BillDetail> details;
		public Bill(String number, Date billDate, String billReason, List<BillDetail> details) {
			super();
			this.number = number;
			this.billDate = billDate;
			this.billReason = billReason;
			this.details = details;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public Date getBillDate() {
			return billDate;
		}
		public void setBillDate(Date billDate) {
			this.billDate = billDate;
		}
		public String getBillReason() {
			return billReason;
		}
		public void setBillReason(String billReason) {
			this.billReason = billReason;
		}
		public List<BillDetail> getDetails() {
			return details;
		}
		public void setDetails(List<BillDetail> details) {
			this.details = details;
		}
		
	}