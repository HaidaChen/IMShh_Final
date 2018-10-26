package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.busdata.supplier.domain.Supplier;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.HistorySupplier;

public interface IMaterialSupplierSerivce {
	List<Supplier> getSuppliers(Material material);
	void insert(HistorySupplier materialSupplier);
}
