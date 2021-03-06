package com.douniu.imshh.material.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Inventory;
import com.douniu.imshh.material.domain.InventoryDetail;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.ICategoryService;
import com.douniu.imshh.material.service.IInventoryService;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;

@Controller
@RequestMapping("/mtlinv")
public class InventoryAction {
	@Autowired
	private IInventoryService service;
	@Autowired
	private ICategoryService ctgService;
	@Autowired
	private IMaterialService mtlService;
	
	
	@RequestMapping(value ="/loadSystemInventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadSystemInventory(MaterialFilter filter){
		PageResult pr = service.getSystemInventory(filter);
		return GsonUtil.toJson(pr);
	}
	
	@Authorization("0205")
	@RequestMapping(value="/inventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void inventory(){
		Inventory inv = new Inventory();
		inv.setInventoryDate(new Date());
		service.inventory(inv);
	}
	
	@Authorization("0205")
	@RequestMapping(value="/reset", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void reset(){
		service.initCashInventory();
	}
	
	@Authorization("0205")
	@RequestMapping(value="/saveInventoryItem", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void saveInventoryItem(String materialId, float quantity){
		service.saveCacheItem(materialId, quantity);
	}
	
	@Authorization("0206")
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
	
	@Authorization("0206")
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
	
	@Authorization("0205")
	@RequestMapping(value = "exportInventory", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportInventory(HttpServletRequest request, HttpServletResponse response, MaterialFilter filter){
		SheetData data = new SheetData("材料盘点"+DateUtil.Date2String(new Date(), "yyyyMMdd"));
		
		if (!StringUtils.isEmpty(filter.getCtgCode())){
			data.put("mtlCtg", ctgService.getByCode(filter.getCtgCode()));
		}
		data.put("mtlName", filter.getName());
		List<Material> materials = mtlService.query(filter);
		data.addDatas(materials);
		ImportAndExportUtil.export("材料系统盘点.xls", data, request, response);
	}
	
	@Authorization("0205")
	@RequestMapping(value="importInventory",method={RequestMethod.GET,RequestMethod.POST}, produces = "text/html; charset=utf-8")  
    @ResponseBody
	public String importInventory(HttpServletRequest request) throws Exception{
		List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
		List<InventoryDetail> inventoryDetails = new ArrayList<>();
		for (List<Object> rowData : data){
			InventoryDetail invDetail = new InventoryDetail();
			Material m = new Material();
			m.setName(rowData.get(0).toString());
			m.setSpecification(rowData.get(1).toString());
			invDetail.setMaterial(m);
			invDetail.setActualQuantity(new Float(rowData.get(3).toString()));
			inventoryDetails.add(invDetail);
		}
		List<ImportException> checkResults = service.checkImport(inventoryDetails);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importCacheItem(inventoryDetails);
        return "success";
	}
}
