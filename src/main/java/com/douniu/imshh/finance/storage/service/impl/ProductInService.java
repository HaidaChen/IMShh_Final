package com.douniu.imshh.finance.storage.service.impl;

import java.util.List;

import com.douniu.imshh.finance.storage.dao.IProductInDao;
import com.douniu.imshh.finance.storage.domain.ProductIn;
import com.douniu.imshh.finance.storage.service.IProductInService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ProductInService implements IProductInService{
	private IProductInDao dao;
	
	@Override
	public List<ProductIn> queryDetail(ProductIn storage) {
		ProductIn condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"condition"});
		return dao.queryDetail(condition);
	}

	@Override
	public List<ProductIn> queryDetailNoPage(ProductIn storage) {
		ProductIn condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"condition"});
		return dao.queryDetailNoPage(condition);
	}

	@Override
	public int count(ProductIn storage) {
		ProductIn condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"condition"});
		return dao.countDetail(condition);
	}

	@Override
	public ProductIn getById(String id) {
		return dao.findDetailById(id);
	}

	@Override
	public void save(ProductIn storage) {
		if (storage.getId().equals("")){
			storage.setId(System.currentTimeMillis()+"");
			storage.setStatus(1);
			dao.insert(storage);
		}else{
			dao.update(storage);
		}
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<ProductIn> storages) {
		dao.batchInsert(storages);
	}

	@Override
	public List<ProductIn> statisticsStorage(ProductIn storage) {
		ProductIn condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"pdtNo"});
		return dao.statisticsStorage(condition);
	}

	@Override
	public int countStorage(ProductIn storage) {
		ProductIn condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"pdtNo"});
		return dao.countStorage(condition);
	}

	public void setDao(IProductInDao dao) {
		this.dao = dao;
	}	
}
