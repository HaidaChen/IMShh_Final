package com.douniu.imshh.common;

public class ImportException {
	private String exception;
	private String desc;
	private String rows;
	private String solution;
	
	public ImportException(){}

	public ImportException(String exception, String desc, String rows, String solution) {
		super();
		this.exception = exception;
		this.desc = desc;
		this.rows = rows;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
}
