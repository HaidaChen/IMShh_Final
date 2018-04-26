package com.douniu.imshh.finance.storage.service.impl;

import java.util.List;

import com.douniu.imshh.finance.storage.dao.IStorageDao;
import com.douniu.imshh.finance.storage.domain.Storage;
import com.douniu.imshh.finance.storage.service.IStorageService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class StorageService implements IStorageService{
	private IStorageDao dao;
	
	@Override
	public List<Storage> queryDetail(Storage storage) {
		Storage condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"pdtNo"});
		return dao.queryDetail(condition);
	}

	@Override
	public List<Storage> queryDetailNoPage(Storage storage) {
		Storage condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"pdtNo"});
		return dao.queryDetailNoPage(condition);
	}

	@Override
	public int count(Storage storage) {
		Storage condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"pdtNo"});
		return dao.countDetail(condition);
	}

	@Override
	public Storage getById(String id) {
		return dao.findDetailById(id);
	}

	@Override
	public void save(Storage storage) {
		if (storage.getId().equals("")){
			storage.setId(System.currentTimeMillis()+"");
			storage.setStatus(1);
			dao.insert(storage);
		}else{
			dao.update(storage);
		}
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<Storage> storages) {
		dao.batchInsert(storages);
	}

	@Override
	public List<Storage> statisticsStorage(Storage storage) {
		Storage condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"pdtNo"});
		return dao.statisticsStorage(condition);
	}

	@Override
	public int countStorage(Storage storage) {
		Storage condition = LikeFlagUtil.appendLikeFlag(storage, new String[]{"pdtNo"});
		return dao.countStorage(condition);
	}

	public void setDao(IStorageDao dao) {
		this.dao = dao;
	}	
}
