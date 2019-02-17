package com.douniu.imshh.finance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.ImportException;
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
		injectorFullName(subject);
		dao.insert(subject);
	}

	@Override
	public void updateSubject(Subject subject) {
		injectorFullName(subject);
		dao.update(subject);
	}
	
	@Override
	public List<ImportException> checkImport(List<Subject> subjects) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String repeation = "";
		List<Subject> fullSubject = dao.query(new FinanceFilter());
		
		for (int i = 0; i < subjects.size(); i++){
			Subject sbj = subjects.get(i);
			if (fullSubject.contains(sbj)){
				repeation += "," + (i+2);
			}
		}
		if (!repeation.equals("")){
			exceptions.add(new ImportException("存在重复的会计科目", "会计科目重复的判断依据是，科目编号", repeation.substring(1), ""));
		}
		return exceptions;
	}

	@Override
	public void importSubject(List<Subject> subjects) {
		dao.batchInsert(subjects);
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
	
	private void injectorFullName(Subject subject){
		String pid = subject.getParent().getId();
		Subject parent = dao.getById(pid);
		if (parent == null){
			subject.setFullName(subject.getCode() + " " + subject.getName());
		}else {
			subject.setFullName(parent.getFullName() + " " + subject.getCode() + " " + subject.getName());
		}
	}
}
