package com.douniu.imshh.sys.domain;

public class Parameter {
	private String pname;
	private String pvalue;
	private String pvtype;
	private int display;
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	public String getPvtype() {
		return pvtype;
	}
	public void setPvtype(String pvtype) {
		this.pvtype = pvtype;
	}
	public int getDisplay() {
		return display;
	}
	public void setDisplay(int display) {
		this.display = display;
	}
	@Override
	public String toString() {
		return "Parameter [pname=" + pname + ", pvalue=" + pvalue + ", pvtype=" + pvtype + ", display=" + display + "]";
	}
	
}
