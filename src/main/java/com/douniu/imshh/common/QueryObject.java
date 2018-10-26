package com.douniu.imshh.common;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class QueryObject<T> {
	private int duration = -1;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
    private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
    private Date endDate;
    private int currentPage;
    private int pageOffset = 0;
    private int pageSize = 0;
    private String condition;
    private String orderBy;
    private String order;
    private T domain;
    
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageOffset() {
		return pageOffset;
	}
	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public T getDomain() {
		return domain;
	}
	public void setDomain(T domain) {
		this.domain = domain;
	}
}
