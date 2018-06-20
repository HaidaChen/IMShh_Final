package com.douniu.imshh.finance.reception.service.impl;

import java.util.List;

import com.douniu.imshh.finance.reception.dao.ISettlementDao;
import com.douniu.imshh.finance.reception.domain.Settlement;
import com.douniu.imshh.finance.reception.service.ISettlementService;

public class SettlementService implements ISettlementService{

	private ISettlementDao dao;
	
	@Override
	public List<Settlement> query(Settlement settlement) {
		return dao.query(settlement);
	}

	@Override
	public Settlement findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Settlement findLastOne() {
		return dao.findLastOne();
	}

	@Override
	public void insert(Settlement settlement) {
		dao.insertDetails(settlement.getDetails());
		dao.insert(settlement);
	}

	@Override
	public void update(Settlement settlement) {
		dao.update(settlement);
	}

	@Override
	public void delete(Settlement settlement) {
		dao.delete(settlement);
	}

	public ISettlementDao getDao() {
		return dao;
	}

	public void setDao(ISettlementDao dao) {
		this.dao = dao;
	}
	
	

}
