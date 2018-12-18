package com.douniu.imshh.finance.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Subject;
import com.douniu.imshh.finance.service.ISubjectService;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/finsub")
public class SubjectAction {
	@Autowired
	private ISubjectService service;
	
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(FinanceFilter filter){
		List<Subject> subjects = service.query(filter);
		return GsonUtil.toJson(subjects);
	}	
	
	@RequestMapping(value ="/getJSTree", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getJSTree(String categoryId){
		List<Subject> subjects = service.getByCategory(categoryId);
		List<JSTreeItem> jsTree = new ArrayList<>();
		
		for (Subject subject : subjects){
			JSTreeItem treeItem;
			if (subject.getParent() == null)
				treeItem = new JSTreeItem(subject.getId(), subject.getCode() + " " + subject.getName(), "#");
			else
				treeItem = new JSTreeItem(subject.getId(), subject.getCode() + " " + subject.getName(), subject.getParent().getId());
			treeItem.putState("opened", true);
			/*treeItem.putA_attr("title", subject.getRemark());*/
			treeItem.putA_attr("fullName", subject.getFullName());
			jsTree.add(treeItem);
		}
		return GsonUtil.toJson(jsTree, null);
	}
	
	@RequestMapping(value ="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		Subject subject = service.getById(id);
		return GsonUtil.toJson(subject);
	}
	
	@RequestMapping(value="/addSubject", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void addSubject(Subject subject){
		service.newSubject(subject);
	}
	
	@RequestMapping(value="/updateSubject", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateSubject(Subject subject){
		service.updateSubject(subject);
	}
	
	@RequestMapping(value="/validateUnique", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String validateUnique(String code){
		boolean result = service.exist(code);
		Map<String, Boolean> map = new HashMap<>();
		map.put("valid", !result);
		return GsonUtil.toJson(map);
	}
	
	
	@RequestMapping(value="/deleteSubject")
	@ResponseBody
	public void deleteSubject(String id){
		service.deleteSubject(id);
	}
	
	/*private String getFullName(Subject subject, List<Subject> allSubject){
		String fullName = "";
		if (subject.getParent() != null){
			fullName += getFullName(subject.getParent(), allSubject) + "_";
		}
		fullName += subject.getCode() + " " + subject.getName();
		
		return fullName;
	}*/
	
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
