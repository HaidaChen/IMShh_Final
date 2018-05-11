package com.douniu.imshh.finance.reception.service.impl;

import java.util.List;

import com.douniu.imshh.finance.reception.dao.IReceptionDao;
import com.douniu.imshh.finance.reception.domain.Reception;
import com.douniu.imshh.finance.reception.service.IReceptionService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ReceptionService implements IReceptionService{
	
	private IReceptionDao dao;
	
	@Override
	public List<Reception> query(Reception reception) {
		Reception condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"condition"});
		return dao.query(condition);
	}

	@Override
	public List<Reception> queryNoPage(Reception reception) {
		Reception condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"condition"});
		return dao.queryNoPage(condition);
	}

	@Override
	public int count(Reception reception) {
		Reception condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"condition"});
		return dao.count(condition);
	}

	@Override
	public Reception getById(String id) {
		return dao.findById(id);
	}

	@Override
	public void save(Reception reception) {
		if (reception.getId().equals("")){
			reception.setId(System.currentTimeMillis()+"");
			reception.setStatus(1);
			dao.insert(reception);
		}else{
			dao.update(reception);
		}
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<Reception> receptions) {
		dao.batchInsert(receptions);
	}

	public void setDao(IReceptionDao dao) {
		this.dao = dao;
	}

	
}
