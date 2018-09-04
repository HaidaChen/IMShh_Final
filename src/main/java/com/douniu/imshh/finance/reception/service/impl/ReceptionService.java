package com.douniu.imshh.finance.reception.service.impl;

import java.util.List;

import com.douniu.imshh.finance.reception.dao.IReceptionDao;
import com.douniu.imshh.finance.reception.domain.Reception;
import com.douniu.imshh.finance.reception.service.IReceptionService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ReceptionService implements IReceptionService{
	private IReceptionDao dao;
	
	@Override
	public List<Reception> queryReception(Reception reception) {
		Reception condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"orderIdentify"});
		return dao.queryReception(condition);
	}


	@Override
	public int countReception(Reception reception) {
		Reception condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"orderIdentify"});
		return dao.countReception(condition);
	}


	@Override
	public float getTotalDebt() {
		// TODO Auto-generated method stub
		return dao.getTotalDebt();
	}

	public void setDao(IReceptionDao dao) {
		this.dao = dao;
	}
}
