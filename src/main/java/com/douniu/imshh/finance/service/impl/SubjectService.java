package com.douniu.imshh.finance.service.impl;

import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.finance.dao.ISubjectDao;
import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Subject;
import com.douniu.imshh.finance.service.ISubjectService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class SubjectService implements ISubjectService{
	private ISubjectDao dao;
	
	@Override
	public List<Subject> query(FinanceFilter filter) {
		FinanceFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"subName", "remark"});
		if (!StringUtils.isEmpty(condition.getSubCode())){
			condition.setSubCode(condition.getSubCode() + "%");
		}
		return dao.query(condition);
	}

	@Override
	public List<Subject> getByCategory(String categoryId) {
		return dao.getByCategory(categoryId);
	}
	
	@Override
	public List<Subject> getByParent(String parentId) {
		return dao.getByParent(parentId);
	}

	@Override
	public List<Subject> queryConfig(FinanceFilter filter) {
		FinanceFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"subName"});
		if (!StringUtils.isEmpty(condition.getSubCode())){
			condition.setSubCode(condition.getSubCode() + "%");
		}
		return dao.queryConfig(condition);
	}

	@Override
	public Subject getById(String id) {
		return dao.getById(id);
	}

	@Override
	public boolean exist(String code) {
		return dao.getByCode(code) != null;
	}

	@Override
	public void newSubject(Subject subject) {
		IDInjector.injector(subject);
		dao.insert(subject);
	}

	@Override
	public void updateSubject(Subject subject) {
		dao.update(subject);
	}

	@Override
	public void setInitBalance(String id, float initBalance) {
		dao.setInitBalance(id, initBalance);
	}

	@Override
	public void setPrivateSubject(String id, int privateSubject) {
		dao.setPrivateSubject(id, privateSubject);
	}

	@Override
	public void deleteSubject(String id) {
		dao.delete(id);
	}

	public void setDao(ISubjectDao dao) {
		this.dao = dao;
	}
}
