package com.douniu.imshh.finance.service;

import java.util.List;

import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Subject;

public interface ISubjectService {
	List<Subject> query(FinanceFilter filter);
	List<Subject> getByCategory(String categoryId);
	List<Subject> getByParent(String parentId);
	List<Subject> queryConfig(FinanceFilter filter);
	
	Subject getById(String id);
	boolean exist(String code);
	
	void newSubject(Subject subject);
	void updateSubject(Subject subject);
	void setInitBalance(String id, float initBlance);
	void setPrivateSubject(String id, int privateSubject);
	void deleteSubject(String id);
}
