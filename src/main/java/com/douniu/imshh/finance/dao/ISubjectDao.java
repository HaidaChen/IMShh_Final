package com.douniu.imshh.finance.dao;

import java.util.List;

import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Subject;

public interface ISubjectDao {
	List<Subject> query(FinanceFilter filter);
	List<Subject> getAll();
	List<Subject> getByCategory(String categoryId);
	List<Subject> getByParent(String parentId);
	Subject getById(String id);
	Subject getByCode(String code);
	
	void insert(Subject subject);
	void update(Subject subject);
	void delete(String id);
}
