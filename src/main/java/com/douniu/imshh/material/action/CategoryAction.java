package com.douniu.imshh.material.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Category;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.ICategoryService;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/mtlCtgy")
public class CategoryAction {
	@Autowired
	private ICategoryService service;
	
	@Authorization("0501")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("0501")
	@RequestMapping(value ="/getJSTree", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getJSTree(MaterialFilter filter){
		List<Category> categories = service.query(filter);
		List<JSTreeItem> jsTree = new ArrayList<>();
		JSTreeItem root = new JSTreeItem("0", "所有分类", "#");
		root.putState("opened", true);
		root.putState("selected", true);
		jsTree.add(root);
		for (Category category : categories){
			JSTreeItem treeItem = new JSTreeItem(category.getId(), category.getCode() + " " + category.getName(), category.getParentId());
			treeItem.putState("opened", true);
			treeItem.putA_attr("title", category.getRemark());
			treeItem.putA_attr("ctgCode", category.getCode());
			treeItem.putA_attr("ctgName", category.getName());
			jsTree.add(treeItem);
		}
		return GsonUtil.toJson(jsTree, null);
	}
	
	@Authorization("0501")
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(MaterialFilter filter){
		List<Category> categories = service.query(filter);
		return GsonUtil.toJson(categories, null);
	}
	
	@Authorization("0501")
	@RequestMapping(value="/getCategoryById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getCategoryById(String id){
		Category category = service.getById(id);
		return GsonUtil.toJson(category);
	}
	
	@RequestMapping(value="/validateCategory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String validateCategory(Category category){
		MaterialFilter filter = new MaterialFilter();
		filter.setCtgCode(category.getCode());
		List<Category> res = service.query(filter);
		Map<String, Boolean> map = new HashMap<>();
		if (res.size() == 0){
			map.put("valid", true);
		}else{
			map.put("valid", false);
		}
		return GsonUtil.toJson(map);
	}
	
	@Authorization("0501")
	@RequestMapping(value="/addCategory", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addCategory(Category category){
		service.newCategory(category);
		return "success";
	}
	
	@Authorization("0501")
	@RequestMapping(value="/updateCategory", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateCategory(Category category){
		service.updateCategory(category);
		return "success";
	}
	
	@Authorization("0501")
	@RequestMapping(value="/deleteCategory")
	@ResponseBody
	public String deleteCategory(String id){
		service.deleteCategory(id);
		return "success";
	}
	
	
	class JSTreeItem{
		private String id;
		private String text;
		private String parent;
		private Map<String, String> a_attr;
		private Map<String, Object> state;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getParent() {
			return parent;
		}
		public void setParent(String parent) {
			this.parent = parent;
		}
		public Map<String, String> getA_attr() {
			return a_attr;
		}
		public void setA_attr(Map<String, String> a_attr) {
			this.a_attr = a_attr;
		}
		public void putA_attr(String attr, String value){
			if (this.a_attr == null){
				this.a_attr = new HashMap<>();
			}
			this.a_attr.put(attr, value);
		}
		public Map<String, Object> getState() {
			return state;
		}
		public void setState(Map<String, Object> state) {
			this.state = state;
		}
		public void putState(String key, Object value){
			if (this.state == null){
				this.state = new HashMap<>();
			}
			this.state.put(key, value);
		}
		
		public JSTreeItem(String id, String text, String parent) {
			super();
			this.id = id;
			this.text = text;
			this.parent = parent;
		}
		public JSTreeItem() {
			super();
			// TODO Auto-generated constructor stub
		}		
	}
	
}
