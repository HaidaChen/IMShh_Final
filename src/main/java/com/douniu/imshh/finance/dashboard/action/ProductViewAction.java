package com.douniu.imshh.finance.dashboard.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.dashboard.domain.ProductIndicators;
import com.douniu.imshh.finance.dashboard.domain.ProductStatistics;
import com.douniu.imshh.finance.dashboard.service.IProductViewService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/productView")
public class ProductViewAction {
	
	@Autowired
	private IProductViewService service;
	
	@RequestMapping(value ="/loadproductview", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadProductView(ProductStatistics pdtView){
		List<ProductStatistics> res = service.queryProduct(pdtView);
		int count = service.countProduct(pdtView);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
	
	@RequestMapping(value ="/loadProductIndicators", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String loadProductIndicators(String year){
		ProductIndicators indicators = service.getIndicators(year);
		if (indicators == null){
			indicators = new ProductIndicators();
		}
		Gson gson = new Gson();
		return gson.toJson(indicators);
	}
}
