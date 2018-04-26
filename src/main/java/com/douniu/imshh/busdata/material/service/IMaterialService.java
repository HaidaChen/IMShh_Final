package com.douniu.imshh.busdata.material.service;

import java.util.List;

import com.douniu.imshh.busdata.material.domain.Material;

public interface IMaterialService {
	List<Material> query(Material material);
	List<Material> queryNoPage(Material material);
	int count(Material material);
	Material getById(String id);
	void save(Material material);
	void delete(String id);
	void batchAdd(List<Material> materials);
}
