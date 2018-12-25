package com.douniu.imshh.order.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.order.domain.Order;
import com.douniu.imshh.order.domain.OrderFilter;
import com.douniu.imshh.order.domain.OrderItem;
import com.douniu.imshh.order.service.IOrderService;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/order")
public class OrderAction {
	@Autowired
	private IOrderService service;
	@Autowired
	private IParameterService pservice;
	
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(OrderFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, "yyyy-MM-dd");
	}
	
	@RequestMapping(value ="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		Order order = service.getById(id);
		return GsonUtil.toJson(order, "yyyy-MM-dd");
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
	
	@RequestMapping(value="/deleteOrder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void deleteOrder(String id){
		service.deleteOrder(id);
	}
}
