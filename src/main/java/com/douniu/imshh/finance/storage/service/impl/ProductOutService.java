package com.douniu.imshh.finance.storage.service.impl;

import java.util.List;

import com.douniu.imshh.finance.storage.dao.IProductOutDao;
import com.douniu.imshh.finance.storage.domain.ProductOut;
import com.douniu.imshh.finance.storage.service.IProductOutService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ProductOutService implements IProductOutService{
	private IProductOutDao dao;
	
	@Override
	public List<ProductOut> query(ProductOut deliver) {
		ProductOut condition = LikeFlagUtil.appendLikeFlag(deliver, new String[]{"condition"});
		return dao.queryDeliverDetail(condition);
	}
	
	@Override
	public List<ProductOut> queryByCustomer(ProductOut deliver) {
		return dao.queryByCustomer(deliver);
	}
	
	@Override
	public List<ProductOut> queryNoPage(ProductOut deliver) {
		ProductOut condition = LikeFlagUtil.appendLikeFlag(deliver, new String[]{"condition"});
		return dao.queryDeliverNoPage(condition);
	}
	
	@Override
	public int count(ProductOut deliver) {
		ProductOut condition = LikeFlagUtil.appendLikeFlag(deliver, new String[]{"condition"});
		return dao.countDeliverDetail(condition);
	}
	
	@Override
	public int countByCustomer(ProductOut deliver) {
		return dao.countByCustomer(deliver);
	}

	@Override
	public ProductOut getById(String id) {
		return dao.findDeliverById(id);
	}
	
	@Override
	public List<ProductOut> findByOrder(String orderIdentify) {
		// TODO Auto-generated method stub
		return dao.findByOrder(orderIdentify);
	}

	@Override
	public void save(ProductOut deliver) {
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
	public void batchAdd(List<ProductOut> delivers) {
		dao.batchInsert(delivers);
	}

	public void setDao(IProductOutDao dao) {
		this.dao = dao;
	}

}
