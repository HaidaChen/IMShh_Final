package com.douniu.imshh.finance.storage.service.impl;

import java.util.List;

import com.douniu.imshh.finance.storage.dao.IDeliverDao;
import com.douniu.imshh.finance.storage.domain.Deliver;
import com.douniu.imshh.finance.storage.service.IDeliverService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class DeliverService implements IDeliverService{
	private IDeliverDao dao;
	
	@Override
	public List<Deliver> query(Deliver deliver) {
		Deliver condition = LikeFlagUtil.appendLikeFlag(deliver, new String[]{"condition"});
		return dao.queryDeliverDetail(condition);
	}
	
	@Override
	public List<Deliver> queryNoPage(Deliver deliver) {
		Deliver condition = LikeFlagUtil.appendLikeFlag(deliver, new String[]{"condition"});
		return dao.queryDeliverNoPage(condition);
	}
	
	@Override
	public int count(Deliver deliver) {
		Deliver condition = LikeFlagUtil.appendLikeFlag(deliver, new String[]{"condition"});
		return dao.countDeliverDetail(condition);
	}

	@Override
	public Deliver getById(String id) {
		return dao.findDeliverById(id);
	}

	@Override
	public void save(Deliver deliver) {
		if (deliver.getId().equals("")){
			deliver.setId(System.currentTimeMillis()+"");
			deliver.setStatus(1);
			dao.insert(deliver);
		}else{
			dao.update(deliver);
		}
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<Deliver> delivers) {
		dao.batchInsert(delivers);
	}

	public void setDao(IDeliverDao dao) {
		this.dao = dao;
	}
}
