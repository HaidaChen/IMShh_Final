package com.douniu.imshh.finance.purchase.service.impl;

import java.util.List;

import com.douniu.imshh.finance.purchase.dao.IPurchaseDao;
import com.douniu.imshh.finance.purchase.domain.PurchaseDetail;
import com.douniu.imshh.finance.purchase.domain.PurchasePlan;
import com.douniu.imshh.finance.purchase.service.IPurchaseDetailService;
import com.douniu.imshh.finance.purchase.service.IPurchaseService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class PurchaseService implements IPurchaseService{
	private IPurchaseDao dao;
	private IPurchaseDetailService dService;
	
	@Override
	public List<PurchasePlan> query(PurchasePlan purchase) {
		// TODO Auto-generated method stub
		PurchasePlan condition = LikeFlagUtil.appendLikeFlag(purchase, new String[]{"identify", "orderIdentify"});
		return dao.query(condition);
	}

	
	@Override
	public int count(PurchasePlan purchase) {
		// TODO Auto-generated method stub
		PurchasePlan condition = LikeFlagUtil.appendLikeFlag(purchase, new String[]{"identify", "orderIdentify"});
		return dao.count(condition);
	}

	@Override
	public PurchasePlan getById(String id) {
		// TODO Auto-generated method stub
		PurchasePlan plan = dao.findById(id);
		if (plan != null){
			List<PurchaseDetail> details = dService.queryByPlan(id);
			plan.setDetails(details);
		}			
		return plan;
	}

	@Override
	public void save(PurchasePlan purchase) {
		// TODO Auto-generated method stub
		/*if (purchase.getId().equals("")){
			purchase.setId(System.currentTimeMillis()+"");
			purchase.setStatus(1);
			dao.insert(purchase);
		}else{
			dao.update(purchase);
		}*/
		
		if (purchase.getId().equals("")){
			String planId = System.currentTimeMillis()+"";
			purchase.setId(planId);
			purchase.setStatus(1);
			for(PurchaseDetail detail : purchase.getDetails()){
				detail.setPlanId(planId);
			}
			dService.batchAdd(purchase.getDetails());
			dao.insert(purchase);
		}else{
			dService.killByPlanId(purchase.getId());
			for(PurchaseDetail detail : purchase.getDetails()){
				detail.setPlanId(purchase.getId());
			}
			dService.batchAdd(purchase.getDetails());
			dao.update(purchase);
		}
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	public void setDao(IPurchaseDao dao) {
		this.dao = dao;
	}


	public void setdService(IPurchaseDetailService dService) {
		this.dService = dService;
	}	
	
	
}
