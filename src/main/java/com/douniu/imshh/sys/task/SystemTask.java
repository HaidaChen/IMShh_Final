package com.douniu.imshh.sys.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.douniu.imshh.sys.service.IParameterService;

@Component
public class SystemTask implements ApplicationContextAware{
	private ApplicationContext applicationContext;
	private IParameterService pService;
	
	/**
	 * 每个月1号凌晨1点执行任务：
	 * 增加账期到字典表中
	 * */
	@Scheduled(cron = "0 0 1 1 * ?")
	public void monthBeginTask(){
		increaseBillPeriod();
	}
	
	private void increaseBillPeriod(){
		Date now=new Date();
		SimpleDateFormat periodFormat = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat periodTextFormat = new SimpleDateFormat("yyyy年第MM期");
        String period = periodFormat.format(now);
        String periodText = periodTextFormat.format(now);
        
        //pService.putDictionary("bill.account.period", period, periodText);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		pService = this.applicationContext.getBean("paramService", IParameterService.class);
		
	}
}
