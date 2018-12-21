package com.douniu.imshh.material.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInOut;
import com.douniu.imshh.material.service.IMaterialInOutService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/mtlio")
public class MaterialInOutAction {
	@Autowired
	private IMaterialInOutService service;
	
	@RequestMapping(value ="/getGlobalInOutPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getGlobalInOutPageResult(MaterialFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		PageResult rs = service.getGlobalInOutPageResult(filter);
		return GsonUtil.toJson(rs, null);
	}
	
	@RequestMapping(value ="/getInOutByMaterial", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getInOutByMaterial(String materialId, String sPeriod, String ePeriod){
		List<MaterialInOut> ios = new ArrayList<>();
		if (!StringUtils.isEmpty(materialId) && 
				!StringUtils.isEmpty(sPeriod) && 
				!StringUtils.isEmpty(ePeriod)){
			ios = service.getInOutByMaterial(materialId, sPeriod, ePeriod);
			
		}
		return GsonUtil.toJson(ios, "yyyy-MM-dd");
	}
}
