package com.douniu.imshh.busdata.product.service.impl;

import java.util.List;

import com.douniu.imshh.busdata.product.dao.IProductDao;
import com.douniu.imshh.busdata.product.domain.Product;
import com.douniu.imshh.busdata.product.service.IProductService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ProductService implements IProductService{

	private IProductDao dao;
	
	@Override
	public List<Product> query(Product product) {
		Product condition = LikeFlagUtil.appendLikeFlag(product, new String[]{"condition"});
		return dao.query(condition);
	}

	@Override
	public List<Product> queryNoPage(Product product) {
		Product condition = LikeFlagUtil.appendLikeFlag(product, new String[]{"condition"});
		return dao.queryNoPage(condition);
	}

	@Override
	public List<Product> queryByOrder(String orderIdt) {
		// TODO Auto-generated method stub
		return dao.queryByOrder(orderIdt);
	}

	@Override
	public int count(Product product) {
		Product condition = LikeFlagUtil.appendLikeFlag(product, new String[]{"condition"});
		return dao.count(condition);
	}

	@Override
	public Product getById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}
	
	@Override
	public Product getByCode(String code) {
		// TODO Auto-generated method stub
		return dao.findByCode(code);
	}

	@Override
	public void save(Product product) {
		// TODO Auto-generated method stub
		if (product.getId().equals("")){
			product.setId(System.currentTimeMillis()+"");
			product.setStatus(1);
			dao.insert(product);
		}else{
			dao.update(product);
		}
	}

	@Override
	public void addStorage(String id, int storage) {
		Product product = dao.findById(id);
		dao.setStorage(id, product.getStorage() + storage);
	}
	
	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<Product> products) {
		dao.batchInsert(products);
	}

	public void setDao(IProductDao dao) {
		this.dao = dao;
	}

	
}
