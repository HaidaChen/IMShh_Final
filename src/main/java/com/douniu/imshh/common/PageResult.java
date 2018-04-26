package com.douniu.imshh.common;

public class PageResult {
	private int resultCount;
    private Object result;
    private int total;
    private Object rows;
    
    public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
    
}
