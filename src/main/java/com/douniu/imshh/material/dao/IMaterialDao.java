package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface IMaterialDao {
	List<Material> getPageResult(MaterialFilter filter);
	
	List<Material> query(MaterialFilter filter);
	
	List<Material> exactQuery(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	Material getById(String id);

	void insert(Material material);
	
	void batchInsert(List<Material> materialList);
	
	void update(Material material);
	
	void setStorage(Material material);
	
	void delete(String id);
}
