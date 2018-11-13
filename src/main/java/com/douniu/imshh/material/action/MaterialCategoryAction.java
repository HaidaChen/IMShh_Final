package com.douniu.imshh.material.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialCategory;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.IMaterialCategoryService;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/mtlCtgy")
public class MaterialCategoryAction {
	@Autowired
	private IMaterialCategoryService service;
	
	@Authorization("010101")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("010101")
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(MaterialFilter filter){
		List<MaterialCategory> categories = service.query(filter);
		return GsonUtil.toJson(categories, null);
	}
	
	@Authorization("010101")
	@RequestMapping(value="/getCategoryById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getCategoryById(String id){
		MaterialCategory category = service.getById(id);
		return GsonUtil.toJson(category);
	}
	
	@Authorization("010102")
	@RequestMapping(value="/addCategory", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void addCategory(MaterialCategory category){
		service.newCategory(category);
	}
	
	@Authorization("010103")
	@RequestMapping(value="/updateCategory", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateCategory(MaterialCategory category){
		service.updateCategory(category);
	}
	
	@Authorization("010104")
	@RequestMapping(value="/deleteCategory")
	@ResponseBody
	public void deleteCategory(String id){
		service.deleteCategory(id);
	}
	
}
