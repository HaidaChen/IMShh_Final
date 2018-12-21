package com.douniu.imshh.material.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialDao;
import com.douniu.imshh.material.dao.IMaterialInOutDao;
import com.douniu.imshh.material.domain.BillDetail;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialBill;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInOut;
import com.douniu.imshh.material.domain.MaterialInOutMap;
import com.douniu.imshh.material.domain.MaterialOutBill;
import com.douniu.imshh.material.domain.PeriodInOut;
import com.douniu.imshh.material.service.IMaterialInOutService;
import com.douniu.imshh.utils.DateUtil;

public class MaterialInOutService implements IMaterialInOutService{
	private IMaterialInOutDao dao;
	private IMaterialDao mdao;
	
	public void insert(MaterialBill bill){
		boolean in = true;
		if (bill instanceof MaterialOutBill){
			in = false;
		}
		List<MaterialInOut> details = new ArrayList<>();
		for (BillDetail detail : bill.getDetails()){
			MaterialInOut inout = new MaterialInOut();
			inout.setBill(bill);
			inout.setGenDate(bill.getBillDate());
			inout.setBillPeriod(DateUtil.Date2String(bill.getBillDate(), "yyyyMM"));
			inout.setSummary(bill.getBillReason());
			
			inout.setMaterial(detail.getMaterial());
			if (in){
				inout.setInQuantity(detail.getQuantity());
				inout.setInAmount(detail.getAmount());
			}else{
				inout.setOutQuantity(detail.getQuantity());
				inout.setOutAmount(detail.getAmount());
			}
			details.add(inout);
		}
		IDInjector.injector(details);
		dao.batchInsert(details);
	}
	
	public void update(MaterialBill bill){
		delete(bill.getId());
		insert(bill);
	}
	
	public void delete(String billId){
		dao.deleteByBill(billId);
	}
	
	/**
	 * 列举所有原材料的出入明细，明细包含指定月份的期初库存、入库数量、出库数量、当前库存汇总四个指标
	 * */
	public PageResult getGlobalInOutPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		List<MaterialInOutMap> maps = new ArrayList<>();
		// 2、期初库存 = 对应开始单据期的上次盘点库存 + 上次盘点时间的入库数 - 上次盘点时间的出库数；
		List<MaterialInOut> details = dao.getTotalInOut(filter);
		for (MaterialInOut detail : details){
			MaterialInOutMap map = null;
			if (!maps.contains(detail)){
				map = new MaterialInOutMap(detail.getMaterial());
				maps.add(map);
			}else {
				map = maps.get(maps.indexOf(detail));
			}
			map.getIomap().put(detail.getBillPeriod(), new PeriodInOut(detail.getBillPeriod(), detail.getInQuantity(), detail.getOutQuantity()));
		}
		
		pr.setRows(maps);
		pr.setTotal(dao.countTotalInOut(filter));
		return pr;
	}
	
	/**
	 * 列举单个原材料的出入明细，明细包含指定月份的出入流水和入库数量、入库金额、出库数量、出库金额汇总、当前库存汇总五个指标
	 */
	public List<MaterialInOut> getInOutByMaterial(String mtlId, String sPeriod, String ePeriod){
		List<MaterialInOut> result = new ArrayList<>();
		
		//指定周期的出入明细记录
		Date startDate = DateUtil.string2Date(sPeriod + "-01");
		Date endDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0, 4)), new Integer(ePeriod.substring(5)));
		List<MaterialInOut> details = dao.getInOutByMaterial(mtlId, startDate, endDate);
		result.addAll(details);
		
		//入库数合计
		float totalIn = dao.getTotalInQuantity(mtlId, startDate, endDate);
		MaterialInOut ototalIn = new MaterialInOut();
		ototalIn.setSummary("合计入库数量");
		ototalIn.setInQuantity(totalIn);
		result.add(ototalIn);
		
		//出库数合计
		float totalOut = dao.getTotalOutQuantity(mtlId, startDate, endDate);
		MaterialInOut ototalOut = new MaterialInOut();
		ototalOut.setSummary("合计出库数量");
		ototalOut.setOutQuantity(totalOut);
		result.add(ototalOut);
		
		//当前库存
		Material mtl = mdao.getById(mtlId);
		MaterialInOut storage = new MaterialInOut();
		storage.setInQuantity(mtl.getStorage());
		storage.setSummary("当前库存");
		result.add(storage);
		return result;
	}

	public void setDao(IMaterialInOutDao dao) {
		this.dao = dao;
	}

	public void setMdao(IMaterialDao mdao) {
		this.mdao = mdao;
	}
	
}
