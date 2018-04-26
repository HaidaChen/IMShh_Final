package com.douniu.imshh.busdata.material.dao;

import java.util.List;

import com.douniu.imshh.busdata.material.domain.Material;

public interface IMaterialDao {
	List<Material> query(Material material);
	List<Material> queryNoPage(Material material);
	int count(Material material);
	Material findById(String id);
	void insert(Material material);
	void batchInsert(List<Material> materials);
	void update(Material material);
	void deleteById(String id);
}
