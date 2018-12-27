package com.douniu.imshh.product.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductInOut;
import com.douniu.imshh.product.service.IProductInOutService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/pdtio")
public class ProductInOutAction {
	@Autowired
	private IProductInOutService service;
	
	@RequestMapping(value ="/getGlobalInOutPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getGlobalInOutPageResult(ProductFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		PageResult rs = service.getGlobalInOutPageResult(filter);
		return GsonUtil.toJson(rs, null);
	}
	
	@RequestMapping(value ="/getInOutByProduct", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getInOutByProduct(String productId, String sPeriod, String ePeriod){
		List<ProductInOut> ios = new ArrayList<>();
		if (!StringUtils.isEmpty(productId) && 
				!StringUtils.isEmpty(sPeriod) && 
				!StringUtils.isEmpty(ePeriod)){
			ios = service.getInOutByProduct(productId, sPeriod, ePeriod);
			
		}
		return GsonUtil.toJson(ios, "yyyy-MM-dd");
	}
}
