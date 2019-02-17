package com.douniu.imshh.finance.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Subject;
import com.douniu.imshh.finance.service.ISubjectService;
import com.douniu.imshh.sys.domain.Role;
import com.douniu.imshh.sys.domain.User;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;

@Controller
@RequestMapping("/finsub")
public class SubjectAction {
	@Autowired
	private ISubjectService service;
	
	@Authorization("0401")
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(FinanceFilter filter, HttpSession session){
		List<Subject> subjects = service.query(filter);
		filterPrivate(session, subjects);
		return GsonUtil.toJson(subjects);
	}	
	
	@Authorization("0401")
	@RequestMapping(value ="/getJSTree", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getJSTree(String categoryId, HttpSession session){
		List<Subject> subjects = service.getByCategory(categoryId);
		filterPrivate(session, subjects);
		List<JSTreeItem> jsTree = getJSTree(subjects);
		return GsonUtil.toJson(jsTree, null);
	}
	
	@Authorization("0401")
	@RequestMapping(value ="/getFullJSTree", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFullJSTree(HttpSession session){
		List<Subject> allSubjects = service.query(new FinanceFilter());
		filterPrivate(session, allSubjects);
		List<JSTreeItem> jsTree = getJSTree(allSubjects);
		return GsonUtil.toJson(jsTree, null);
	}	
	
	@Authorization("0402")
	@RequestMapping(value ="/queryConfig", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryConfig(FinanceFilter filter){
		List<Subject> subjects = service.queryConfig(filter);
		return GsonUtil.toJson(subjects);
	}
	
	@Authorization("0401")
	@RequestMapping(value ="/getFirstSubject", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFirstSubject(){
		List<Subject> allSubjects = service.query(new FinanceFilter());
		return GsonUtil.toJson(allSubjects.get(0), null);
	}
	
	@Authorization("0401")
	@RequestMapping(value ="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		Subject subject = service.getById(id);
		return GsonUtil.toJson(subject);
	}
	
	@Authorization("0401")
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
	
	@Authorization("0402")
	@RequestMapping(value="/setInitBalance", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String setInitBalance(String subId, float initBalance){
		service.setInitBalance(subId, initBalance);
		return "success";
	}
	
	@Authorization("0402")
	@RequestMapping(value="/setPrivateSubject", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String setPrivateSubject(String subId, int privateSubject){
		service.setPrivateSubject(subId, privateSubject);
		return "success";
	}
	
	@RequestMapping(value="/validateUnique", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String validateUnique(String code){
		boolean result = service.exist(code);
		Map<String, Boolean> map = new HashMap<>();
		map.put("valid", !result);
		return GsonUtil.toJson(map);
	}
	
	@Authorization("0401")
	@RequestMapping(value="/deleteSubject")
	@ResponseBody
	public void deleteSubject(String id){
		service.deleteSubject(id);
	}
	
	@Authorization("0401")
	@RequestMapping(value="importSubject",method={RequestMethod.GET,RequestMethod.POST}, produces = "text/html; charset=utf-8")  
    @ResponseBody
	public String importSubject(HttpServletRequest request) throws Exception{
		List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
        List<Subject> subjects = new ArrayList<>();
		for (List<Object> rowData : data){
			Subject sub = new Subject();
			sub.setId(rowData.get(0).toString());
			sub.setCode(rowData.get(0).toString());
			sub.setName(rowData.get(1).toString());
			sub.setFullName(rowData.get(2).toString());
			Subject parent = new Subject();
			parent.setId(rowData.get(3).toString());
			sub.setParent(parent);
			sub.setCategory(rowData.get(4).toString());
			sub.setRemark(rowData.get(5).toString());
			subjects.add(sub);
		}
		List<ImportException> checkResults = service.checkImport(subjects);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importSubject(subjects);
        return "success";
	}
	
	@Authorization("0401")
	@RequestMapping(value = "exportSubject", method = RequestMethod.GET, produces = "text/html; charset=utf-8")  
    @ResponseBody  
	public void exportSubject(HttpServletRequest request,HttpServletResponse response, FinanceFilter filter){
		SheetData data = new SheetData("会计科目列表");
		
		Map<String, String> ctgMap = new HashMap<>();
		ctgMap.put("01", "01 资产类");
		ctgMap.put("02", "02 负债类");
		ctgMap.put("03", "03 共同类");
		ctgMap.put("04", "04 所有者权益");
		ctgMap.put("05", "05 成本");
		ctgMap.put("06", "06 损益");
		
		List<Subject> subjects = service.query(filter);
		for (Subject subject : subjects){
			subject.setCode(subject.getCode()+".");
			subject.setCategory(ctgMap.get(subject.getCategory()));			
		}
		data.addDatas(subjects);
		ImportAndExportUtil.export("会计科目列表.xls", data, request, response);
	}
	
	private void filterPrivate(HttpSession session, List<Subject> src){
		User user = (User)session.getAttribute("user");
		List<Role> roles = user.getRoles();
		boolean filter = true;
		for (Role role : roles){
			if ("02".equals(role.getId())){
				filter = false;
				break;
			}
		}
		
		List<Subject> filterSubs = new ArrayList<>();
		if (filter){
			for(Subject sub : src){
				if (sub.getPrivateSubject() == 1){
					filterSubs.add(sub);
				}
			}
		}
		src.removeAll(filterSubs);
	}
	
	private List<JSTreeItem> getJSTree(List<Subject> subjects){
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
		return jsTree;
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
