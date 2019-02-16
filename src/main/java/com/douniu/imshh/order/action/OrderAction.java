package com.douniu.imshh.order.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.customer.service.ICustomerService;
import com.douniu.imshh.order.domain.Order;
import com.douniu.imshh.order.domain.OrderFilter;
import com.douniu.imshh.order.domain.OrderItem;
import com.douniu.imshh.order.domain.OrderProductDetail;
import com.douniu.imshh.order.service.IOrderService;
import com.douniu.imshh.product.service.IProductService;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/order")
public class OrderAction {
	@Autowired
	private IOrderService service;
	@Autowired
	private IParameterService pservice;
	@Autowired
	private ICustomerService custService;
	@Autowired
	private IProductService pdtService;
	
	@Authorization("0102")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(OrderFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, "yyyy-MM-dd");
	}
	
	@RequestMapping(value ="/getAll", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAll(){
		List<Order> res = service.getAll();
		return GsonUtil.toJson(res, "yyyy-MM-dd");
	}
	
	@Authorization("0102")
	@RequestMapping(value ="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		Order order = service.getById(id);
		return GsonUtil.toJson(order, "yyyy-MM-dd");
	}
	
	@Authorization("0103")
	@RequestMapping(value ="/getOrderProductPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getOrderProductPageResult(OrderFilter filter){
		PageResult pr = service.getOrderProductPageResult(filter);
		return GsonUtil.toJson(pr, "yyyy-MM-dd");
	}
	
	@RequestMapping(value ="/getBillCode", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBillCode(){
		String code = pservice.getParam("bill.order.code");
		String preFix = "DGHT";
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(new Date());
		if (StringUtils.isEmpty(code)){
			code = preFix + dateStr + ".001";
		}else{
			String _dateStr = code.substring(preFix.length(), code.indexOf("."));
			if (!dateStr.equals(_dateStr)){
				code = preFix + dateStr + ".001";
			}else{
				String num = code.substring(code.indexOf(".") + 1);
				String new_num = String.format("%0" + num.length() + "d", Integer.parseInt(num) + 1);
				code = preFix + _dateStr + "." + new_num;
			}
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		return GsonUtil.toJson(map);
	}
	
	@Authorization("0101")
	@RequestMapping(value="/newOrder", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void newOrder(Order order, String billItem){
		List<OrderItem> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<OrderItem>>(){}.getType());
		List<OrderItem> details = new ArrayList<>();
		for (OrderItem detail : _details){
			if (detail.getProduct()!= null && !StringUtils.isEmpty(detail.getProduct().getId())){
				details.add(detail);
			}
		}
		order.setItems(details);
		service.newOrder(order);
		pservice.setParam("bill.order.code", order.getIdentify());
	}
	
	@Authorization("0101")
	@RequestMapping(value="/updateOrder", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateOrder(Order order, String billItem){
		List<OrderItem> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<OrderItem>>(){}.getType());
		List<OrderItem> details = new ArrayList<>();
		for (OrderItem detail : _details){
			if (detail.getProduct()!= null && !StringUtils.isEmpty(detail.getProduct().getId())){
				details.add(detail);
			}
		}
		order.setItems(details);
		service.updateOrder(order);
	}
	
	@Authorization("0101")
	@RequestMapping(value="/deleteOrder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void deleteOrder(String id){
		service.deleteOrder(id);
	}
	
	@Authorization("0102")
	@RequestMapping(value = "exportOrder", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportOrder(HttpServletRequest request, HttpServletResponse response, OrderFilter filter){
		SheetData data = new SheetData("订单列表");
		data.put("identify", filter.getIdentify());
		data.put("orderType", filter.getOrderType().equals("0") ? "" : filter.getOrderType().equals("1") ? "海外订单" : "国内订单");
		if (!filter.getCustomerId().equals("")){
			data.put("customer", custService.getById(filter.getCustomerId()).getName());
		}
		data.put("startDate", filter.getStartDate());
		data.put("endDate", filter.getEndDate());
		List<Order> orders = service.query(filter);
		data.addDatas(orders);
		ImportAndExportUtil.export("订单列表.xls", data, request, response);
	}
	
	@Authorization("0103")
	@RequestMapping(value = "exportProdcutOrder", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportProdcutOrder(HttpServletRequest request, HttpServletResponse response, OrderFilter filter){
		SheetData data = new SheetData("成品订购明细");
		if (!StringUtils.isEmpty(filter.getProductId()))
			data.put("product", pdtService.getById(filter.getProductId()).getCode());
		data.put("startDate", filter.getStartDate());
		data.put("endDate", filter.getEndDate());
		List<OrderProductDetail> orders = service.queryOrderProduct(filter);
		data.addDatas(orders);
		ImportAndExportUtil.export("成品订购明细.xls", data, request, response);
	}
}
