package com.douniu.imshh.material.domain;

import java.util.Date;

import com.douniu.imshh.busdata.supplier.domain.Supplier;

public class MaterialIn {
	private Material material;
	private HistorySupplier supplier;
	
	private String id;
	private Date date;
	private float amount;
	private float price;
	private float total;
	private String remark;
	private int status = 1;
}
