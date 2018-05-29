package com.douniu.imshh.finance.storage.service.impl;

import java.util.List;

import com.douniu.imshh.finance.storage.dao.IMaterialOutDao;
import com.douniu.imshh.finance.storage.domain.MaterialOut;
import com.douniu.imshh.finance.storage.service.IMaterialOutService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialOutService implements IMaterialOutService{
	
	private IMaterialOutDao dao;
	
	@Override
	public List<MaterialOut> query(MaterialOut out) {
		MaterialOut condition = LikeFlagUtil.appendLikeFlag(out, new String[]{"condition"});
		return dao.query(condition);
	}

	@Override
	public List<MaterialOut> queryNoPage(MaterialOut out) {
		MaterialOut condition = LikeFlagUtil.appendLikeFlag(out, new String[]{"condition"});
		return dao.queryNoPage(condition);
	}

	@Override
	public int count(MaterialOut out) {
		MaterialOut condition = LikeFlagUtil.appendLikeFlag(out, new String[]{"condition"});
		return dao.count(condition);
	}
	
	@Override
	public MaterialOut getById(String id) {
		return dao.findById(id);
	}

	@Override
	public void save(MaterialOut out) {
		if (out.getId().equals("")){
			out.setId(System.currentTimeMillis()+"");
			out.setStatus(1);
			dao.insert(out);
		}else{
			dao.update(out);
		}
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<MaterialOut> outs) {
		dao.batchInsert(outs);
	}

	public void setDao(IMaterialOutDao dao) {
		this.dao = dao;
	}

	
}
