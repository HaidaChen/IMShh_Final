package com.douniu.imshh.finance.reception.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.order.domain.Order;
import com.douniu.imshh.finance.order.service.IOrderService;
import com.douniu.imshh.finance.reception.domain.Reception;
import com.douniu.imshh.finance.reception.domain.SettDetail;
import com.douniu.imshh.finance.reception.domain.Settlement;
import com.douniu.imshh.finance.reception.service.IReceptionService;
import com.douniu.imshh.finance.reception.service.ISettlementService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/reception")
public class ReceptionAction {
	@Autowired
	private IReceptionService serivce;
	@Autowired
	private ISettlementService setService;
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value ="/statistics", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String statistics(Reception reception){		
		reception.setStartDate(getLaterDate(reception));
		Reception _reception = serivce.statistics(reception);
		Gson gson = new Gson();
        return gson.toJson(_reception);
	}
	
	@RequestMapping(value ="/statisticsByOrder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String statisticsByOrder(Reception reception){
		List<Reception> result = new ArrayList<>();
		
		Settlement condition = new Settlement();		
		condition.setStartDate(reception.getStartDate());
		condition.setEndDate(reception.getEndDate());
		List<Settlement> settlements = setService.query(condition);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Settlement settlement : settlements){
			Reception rep = new Reception();
			String date = sdf.format(settlement.getSettlementDate());
			rep.setOrderIdentify(date + "结转");
			rep.setSettlement(true);
			rep.setCustomerName(settlement.getId());
			rep.setReception(settlement.getReception());
			rep.setPayment(settlement.getPayment());
			result.add(rep);
		}
		
		reception.setStartDate(getLaterDate(reception));
		List<Reception> receptions = serivce.statisticsByOrder(reception);
		result.addAll(receptions);
		PageResult pr = new PageResult();
		pr.setTotal(result.size());
		pr.setRows(result);
		
		Gson gson = new Gson();
		return gson.toJson(pr);
	}
	
	@RequestMapping(value ="/perSettlement", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String perSettlement(){
		Settlement perSett = new Settlement();
		Settlement lastOne = setService.findLastOne();
		
		float reception = 0;
		float paid = 0;
		
		Order condition = new Order();
		if (lastOne != null)
		condition.setStartDate(lastOne.getSettlementDate());
		condition.setEndDate(new Date(System.currentTimeMillis()));
		condition.setState("1");
		List<Order> orders = orderService.queryNoPage(condition);
		List<SettDetail> details = new ArrayList<>();
		for (Order order : orders){
			SettDetail detail = new SettDetail();
			detail.setOrderIdentify(order.getIdentify());
			detail.setCustName(order.getCustName());
			detail.setOrderDate(order.getOrderDate());
			detail.setAmountDollar(order.getAmountDollar());
			detail.setAmountRMB(order.getAmountRMB());
			detail.setPaid(order.getPaid());
			reception += order.getAmountRMB();
			paid += order.getPaid();
			details.add(detail);
		}
		
		perSett.setSettlementDate(new Date(System.currentTimeMillis()));
		if (lastOne != null)
		perSett.setLastSettlement(lastOne.getSettlementDate());
		perSett.setReception(reception);
		perSett.setPayment(paid);
		perSett.setDetails(details);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(perSett);
	}
	
	@RequestMapping(value="/settlement", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int settlement(Settlement settlement, String settDetails){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<SettDetail> details =gson.fromJson(settDetails, new TypeToken<List<SettDetail>>() {}.getType());
		settlement.setDetails(details);
		setService.insert(settlement);
        return 1;
	}
	
	@RequestMapping(value ="/findSettlement", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findSettlement(String id){
		Settlement sett = setService.findById(id);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(sett);
	}
	
	private Date getLaterDate(Reception reception){
		Date startDate = null;
		Settlement lastone = setService.findLastOne();
		if (lastone == null){
			startDate = reception.getStartDate();
		}else{
			if (lastone.getSettlementDate().getTime() - reception.getStartDate().getTime() > 0){
				startDate = lastone.getSettlementDate();
			}else{
				startDate = reception.getStartDate();
			}
		}
		reception.setStartDate(startDate);
		return startDate;
	}
}
