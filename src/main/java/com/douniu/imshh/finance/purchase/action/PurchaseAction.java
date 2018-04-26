package com.douniu.imshh.finance.purchase.action;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.purchase.domain.DeliverDetail;
import com.douniu.imshh.finance.purchase.domain.PurchaseDetail;
import com.douniu.imshh.finance.purchase.domain.PurchasePlan;
import com.douniu.imshh.finance.purchase.service.IDeliverDetailService;
import com.douniu.imshh.finance.purchase.service.IPurchaseDetailService;
import com.douniu.imshh.finance.purchase.service.IPurchaseService;
import com.douniu.imshh.utils.ReflectionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/purchase")
public class PurchaseAction {
	
	@Autowired
	private IPurchaseService service;
	@Autowired
	private IPurchaseDetailService detailService;
	@Autowired
	private IDeliverDetailService deliverService;
	
	@RequestMapping("/main")
    public ModelAndView enter(PurchasePlan purchase){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/finance/purchase/overview");
        return mav;
    }
	
	@RequestMapping("/edit")
	public ModelAndView edit(PurchasePlan purchase){
		ModelAndView mav = new ModelAndView();
		if (purchase.getId() != ""){
			PurchasePlan oPurchase = service.getById(purchase.getId());
			mav.addObject("purchase", oPurchase);
		}
        mav.setViewName("/finance/purchase/edit");
        return mav;
	}
	
	@RequestMapping("/save")
	public ModelAndView save(PurchasePlan purchase){
		service.save(purchase);
        return enter(purchase);		
	}
	
	@RequestMapping(value ="/loadpurchase", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadPurchase(PurchasePlan purchase){
		List<PurchasePlan> res = service.query(purchase);
		int count = service.count(purchase);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
	
	@RequestMapping(value ="/loadPurchaseDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadPurchaseDetail(String planId){
		List<PurchaseDetail> details = detailService.queryByPlan(planId);		
		Gson gson = new Gson();
		return gson.toJson(details);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
		detailService.deleteByPlanId(id);
		deliverService.deleteByPlan(id);
	}
	
	@RequestMapping("/mainDeliver")
	public ModelAndView mainDeliver(DeliverDetail deliverDetail){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/finance/purchase/deliver");
        return mav;
	}
	
	@RequestMapping(value ="/loaddeliverdetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadDeliverDetail(DeliverDetail deliverDetail){
		List<DeliverDetail> res = deliverService.query(deliverDetail);
		int count = deliverService.count(deliverDetail);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
	
	
	@RequestMapping("/editDeliver")
	public ModelAndView editDeliver(DeliverDetail deliver ){
		ModelAndView mav = new ModelAndView();
		
		if (deliver.getId() != null && deliver.getId() != ""){
			DeliverDetail oDeliver = deliverService.getById(deliver.getId());
			mav.addObject("deliver", oDeliver);
		}else{
			String detailId = deliver.getPlanDetailId();
			PurchaseDetail detail = detailService.queryById(detailId);
			DeliverDetail oDeliver = createByPlanDetail(detail);
			oDeliver.setPlanDetailId(detailId);
			mav.addObject("deliver", oDeliver);
		}
        mav.setViewName("/finance/purchase/deliverEdit");
        return mav;
	}
	
	@RequestMapping(value = "/saveDeliver", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveDeliver(DeliverDetail deliver){
		deliverService.save(deliver);
		Gson gson = new Gson();
		return gson.toJson(deliver);	
	}
	
	private DeliverDetail createByPlanDetail(PurchaseDetail detail){
		DeliverDetail deliverDetail = new DeliverDetail();
		Field[] fields = PurchaseDetail.class.getDeclaredFields();
		for(Field field : fields){
			if ("id".equals(field.getName()))
				continue;
			Field target = ReflectionUtil.getDeclaredField(deliverDetail, field.getName());
			if (target != null){
				Object value = ReflectionUtil.getFieldValue(detail, field.getName());
				if (value != null){
					ReflectionUtil.setFieldValue(deliverDetail, target.getName(), value);
				}
			}
		}
		deliverDetail.setDeliverDate(new Date());
		
		return deliverDetail;
	}
}
