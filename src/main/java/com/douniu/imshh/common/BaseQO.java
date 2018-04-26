package com.douniu.imshh.common;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BaseQO {
	private int duration = -1;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
    private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
    private Date endDate;
    private int currentPage;
    private int pageOffset = 0;
    private int pageSize = 0;
    private String condition;
    
    
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        if (duration != -1){
            Calendar startDate = Calendar.getInstance();
            switch (duration) {
            case 0:
                startDate.add(Calendar.DATE, -7);//日期回滚7天
                break;
            case 1:
                startDate.add(Calendar.DATE, -30);//日期回滚30天
                break;
            case 2:
                startDate.add(Calendar.DATE, -90);//日期回滚90天
                break;
            default:
                break;
            }
            setStartDate(startDate.getTime());
            setEndDate(new Date());
        }
        this.duration = duration;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageOffset() {
    	//this.pageOffset = (currentPage - 1) * pageSize;
        return pageOffset;
    }
    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
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
    public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;		
	}
	@Override
	public String toString() {
		return "BaseQO [duration=" + duration + ", startDate=" + startDate + ", endDate=" + endDate + ", pageOffset="
				+ pageOffset + ", pageSize=" + pageSize + ", condition=" + condition + "]";
	}	
	
}
