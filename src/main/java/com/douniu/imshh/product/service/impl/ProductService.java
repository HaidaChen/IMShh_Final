package com.douniu.imshh.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.dao.IProductDao;
import com.douniu.imshh.product.domain.Product;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.service.IProductService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ProductService implements IProductService{

	private IProductDao dao;
	
	@Override
	public PageResult getPageResult(ProductFilter filter) {
		PageResult pr = new PageResult();
		ProductFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"code", "remark"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public List<Product> query(ProductFilter filter) {
		ProductFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"code", "remark"});
		return dao.query(condition);
	}

	@Override
	public List<Product> queryByOrder(String orderIdt) {
		// TODO Auto-generated method stub
		return dao.queryByOrder(orderIdt);
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

	
	/*@Override
	public void save(Product product) {
		// TODO Auto-generated method stub
		if (product.getId().equals("")){
			product.setId(System.currentTimeMillis()+"");
			product.setStatus(1);
			dao.insert(product);
		}else{
			dao.update(product);
		}
	}*/

	@Override
	public void newProduct(Product product) {
		IDInjector.injector(product);
		dao.insert(product);
	}

	@Override
	public void updateProduct(Product product) {
		dao.update(product);
	}

	
	@Override
	public void importProduct(List<Product> productList) {
		IDInjector.injector(productList);
		dao.batchInsert(productList);
	}

	@Override
	public List<ImportException> checkImport(List<Product> productList) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String repeation = "";
		List<Product> fullProduct = dao.query(new ProductFilter());
		
		for (int i = 0; i < productList.size(); i++){
			Product pdt = productList.get(i);
			if (fullProduct.contains(pdt)){
				repeation += "," + (i+2);
			}
		}
		if (!repeation.equals("")){
			exceptions.add(new ImportException("存在重复的成品", "成品重复的判断依据是，成品货号是否重复", repeation.substring(1), ""));
		}
		return exceptions;
	}

	@Override
	public List<Product> exportProduct(ProductFilter filter) {
		return query(filter);
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

	/*@Override
	public void batchAdd(List<Product> products) {
		dao.batchInsert(products);
	}
*/
	public void setDao(IProductDao dao) {
		this.dao = dao;
	}

	
}
