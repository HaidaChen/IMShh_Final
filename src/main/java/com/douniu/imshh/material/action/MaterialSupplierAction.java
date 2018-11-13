package com.douniu.imshh.material.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialSupplier;
import com.douniu.imshh.material.service.IMaterialSupplierService;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/mtlSupp")
public class MaterialSupplierAction {
	@Autowired
	private IMaterialSupplierService service;
	
	@Authorization("010201")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("010201")
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(MaterialFilter filter){
		List<MaterialSupplier> suppliers = service.query(filter);
		return GsonUtil.toJson(suppliers, null);
	}
	
	@Authorization("010201")
	@RequestMapping(value="/getSupplierById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getSupplierById(String id){
		MaterialSupplier supplier = service.getById(id);
		return GsonUtil.toJson(supplier);
	}
	
	@Authorization("010202")
	@RequestMapping(value="/addSupplier", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void addSupplier(MaterialSupplier supplier){
		service.newSupplier(supplier);
	}
	
	@Authorization("010203")
	@RequestMapping(value="/updateSupplier", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateSupplier(MaterialSupplier supplier){
		service.updateSupplier(supplier);
	}
	
	@Authorization("010204")
	@RequestMapping(value="/deleteSupplier")
	@ResponseBody
	public void deleteSupplier(String id){
		service.deleteSupplier(id);
	}
}
