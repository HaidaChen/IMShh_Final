package com.douniu.imshh.busdata.material.service.impl;

import java.util.List;

import com.douniu.imshh.busdata.material.dao.IMaterialDao;
import com.douniu.imshh.busdata.material.domain.Material;
import com.douniu.imshh.busdata.material.service.IMaterialService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialService implements IMaterialService{

	private IMaterialDao dao;
	
	public List<Material> query(Material material) {
		Material condition = LikeFlagUtil.appendLikeFlag(material, new String[]{"condition"});
		return dao.query(condition);
	}

	public List<Material> queryNoPage(Material material) {
		Material condition = LikeFlagUtil.appendLikeFlag(material, new String[]{"condition"});
		return dao.queryNoPage(condition);
	}

	public int count(Material material) {
		Material condition = LikeFlagUtil.appendLikeFlag(material, new String[]{"condition"});
		return dao.count(condition);
	}

	public Material getById(String id) {
		return dao.findById(id);
	}

	public void save(Material material) {
		if (material.getId().equals("")){
			material.setId(System.currentTimeMillis()+"");
			material.setStatus(1);
			dao.insert(material);
		}else{
			dao.update(material);
		}
	}

	public void delete(String id) {
		dao.deleteById(id);
	}

	public void batchAdd(List<Material> materials) {
		dao.batchInsert(materials);
	}

	public void setDao(IMaterialDao dao) {
		this.dao = dao;
	}

	
}
