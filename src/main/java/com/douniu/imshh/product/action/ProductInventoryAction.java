package com.douniu.imshh.product.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.Inventory;
import com.douniu.imshh.product.domain.InventoryDetail;
import com.douniu.imshh.product.domain.Product;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.service.IInventoryService;
import com.douniu.imshh.product.service.IProductService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;

@Controller
@RequestMapping("/pdtinv")
public class ProductInventoryAction {
	@Autowired
	private IInventoryService service;
	@Autowired
	private IProductService pdtService;
	
	@Authorization("0305")
	@RequestMapping(value ="/loadSystemInventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadSystemInventory(ProductFilter filter){
		PageResult pr = service.getSystemInventory(filter);
		return GsonUtil.toJson(pr);
	}
	
	@Authorization("0305")
	@RequestMapping(value="/inventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void inventory(){
		Inventory inv = new Inventory();
		inv.setInventoryDate(new Date());
		service.inventory(inv);
	}
	
	@Authorization("0305")
	@RequestMapping(value="/reset", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void reset(){
		service.initCashInventory();
	}
	
	@Authorization("0305")
	@RequestMapping(value="/saveInventoryItem", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void saveInventoryItem(String productId, float quantity){
		service.saveCacheItem(productId, quantity);
	}
	
	@Authorization("0306")
	@RequestMapping(value ="/getHistroyInventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getHistroyInventory(ProductFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		List<Inventory> rs = service.queryInventory(filter);
		return GsonUtil.toJson(rs, "yyyy-MM-dd");
	}
	
	@Authorization("0306")
	@RequestMapping(value ="/getHistroyInventoryDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getHistroyInventoryDetail(ProductFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		PageResult rs = service.queryInventoryDetailPageResult(filter);
		return GsonUtil.toJson(rs, "yyyy-MM-dd");
	}
	
	@Authorization("0305")
	@RequestMapping(value = "exportInventory", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportInventory(HttpServletRequest request, HttpServletResponse response, ProductFilter filter){
		SheetData data = new SheetData("成品盘点"+DateUtil.Date2String(new Date(), "yyyyMMdd"));
		
		data.put("pdtCode", filter.getCode());
		List<Product> pdts = pdtService.query(new ProductFilter());
		data.addDatas(pdts);
		ImportAndExportUtil.export("成品系统盘点.xls", data, request, response);
	}
	
	@Authorization("0305")
	@RequestMapping(value="importInventory",method={RequestMethod.GET,RequestMethod.POST}, produces = "text/html; charset=utf-8")  
    @ResponseBody
	public String importInventory(HttpServletRequest request) throws Exception{
		List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
		List<InventoryDetail> inventoryDetails = new ArrayList<>();
		for (List<Object> rowData : data){
			InventoryDetail invDetail = new InventoryDetail();
			Product p = new Product();
			p.setName(rowData.get(0).toString());
			p.setModel(rowData.get(1).toString());
			invDetail.setProduct(p);
			invDetail.setActualQuantity(new Integer(rowData.get(2).toString()));
			inventoryDetails.add(invDetail);
		}
		List<ImportException> checkResults = service.checkImport(inventoryDetails);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importCacheItem(inventoryDetails);
        return "success";
	}
}
