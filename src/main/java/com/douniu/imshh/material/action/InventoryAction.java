package com.douniu.imshh.material.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Inventory;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.IInventoryService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/mtlinv")
public class InventoryAction {
	@Autowired
	private IInventoryService service;
	
	@RequestMapping(value ="/loadSystemInventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadSystemInventory(MaterialFilter filter){
		PageResult pr = service.getSystemInventory(filter);
		return GsonUtil.toJson(pr);
	}
	
	@RequestMapping(value="/inventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void inventory(){
		Inventory inv = new Inventory();
		inv.setInventoryDate(new Date());
		service.inventory(inv);
	}
	
	@RequestMapping(value="/reset", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void reset(){
		service.initCashInventory();
	}
	
	@RequestMapping(value="/saveInventoryItem", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void saveInventoryItem(String materialId, float quantity){
		service.saveCacheItem(materialId, quantity);
	}
	
	@RequestMapping(value ="/getHistroyInventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getHistroyInventory(MaterialFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		List<Inventory> rs = service.queryInventory(filter);
		return GsonUtil.toJson(rs, "yyyy-MM-dd");
	}
	
	@RequestMapping(value ="/getHistroyInventoryDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getHistroyInventoryDetail(MaterialFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		PageResult rs = service.queryInventoryDetailPageResult(filter);
		return GsonUtil.toJson(rs, "yyyy-MM-dd");
	}
}
