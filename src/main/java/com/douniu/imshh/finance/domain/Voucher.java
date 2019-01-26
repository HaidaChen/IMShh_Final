package com.douniu.imshh.finance.domain;

import java.util.Date;
import java.util.List;

public class Voucher {
	private String id;
	private Date billDate;
	/*凭证字: 1：记； 2：付；3：收；4：转*/
	private int word;
	/*凭证号*/
	private int number;
	/*凭证字号*/
	private String wordNumber;
	/*制单人*/
	private String preparedBy;
	/*审核人*/
	private String auditor;
	/*凭证状态： 0：未审核，1：已审核*/
	private int billStatus;
	/*凭证分录*/
	private List<VoucherEntry> entries;
	/*附件链接，多个用分号分隔*/
	private String attachments;
	private String remark;
	private int status = 1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public int getWord() {
		return word;
	}
	public void setWord(int word) {
		this.word = word;
		setWordNumber();
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
		setWordNumber();
	}
	public String getWordNumber() {
		return wordNumber;
	}
	public void setWordNumber() {
		String str = "";
		if (this.word != 0 && this.number != 0){
			switch (word) {
			case 1:
				str += "记";
				break;
			case 2:
				str += "付";
				break;
			case 3:
				str += "收";
				break;
			case 4:
				str += "转";
				break;
			default:
				break;
			}
			str += number;
		}
		this.wordNumber = str;
	}
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
	}
	public List<VoucherEntry> getEntries() {
		return entries;
	}
	public void setEntries(List<VoucherEntry> entries) {
		this.entries = entries;
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
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	@Override
	public String toString() {
		return "Voucher [id=" + id + ", billDate=" + billDate + ", word=" + word + ", number=" + number
				+ ", preparedBy=" + preparedBy + ", auditor=" + auditor + ", billStatus=" + billStatus + ", entries="
				+ entries + ", attachments=" + attachments + ", remark=" + remark + ", status=" + status + "]";
	}		
}
