package com.douniu.imshh.finance.storage.service.impl;

import java.util.List;

import com.douniu.imshh.finance.storage.dao.IMaterialInDao;
import com.douniu.imshh.finance.storage.domain.MaterialIn;
import com.douniu.imshh.finance.storage.service.IMaterialInService;
import com.douniu.imshh.material.service.IMaterialInOutService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialInService implements IMaterialInService{
	
	private IMaterialInDao dao;
	private IMaterialInOutService ioService;
	
	@Override
	public List<MaterialIn> query(MaterialIn reception) {
		MaterialIn condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"condition"});
		return dao.query(condition);
	}

	@Override
	public List<MaterialIn> queryNoPage(MaterialIn reception) {
		MaterialIn condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"condition"});
		return dao.queryNoPage(condition);
	}

	@Override
	public int count(MaterialIn reception) {
		MaterialIn condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"condition"});
		return dao.count(condition);
	}
		

	@Override
	public List<MaterialIn> queryBySupplier(MaterialIn reception) {
		return dao.queryBySupplier(reception);
	}

	@Override
	public int countBySupplier(MaterialIn reception) {
		// TODO Auto-generated method stub
		return dao.countBySupplier(reception);
	}

	@Override
	public MaterialIn getById(String id) {
		return dao.findById(id);
	}

	@Override
	public void save(MaterialIn reception) {
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
	public void batchAdd(List<MaterialIn> receptions) {
		dao.batchInsert(receptions);
	}

	public void setDao(IMaterialInDao dao) {
		this.dao = dao;
	}

	
}
