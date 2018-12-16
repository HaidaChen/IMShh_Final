package com.douniu.imshh.finance.action;

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
}
