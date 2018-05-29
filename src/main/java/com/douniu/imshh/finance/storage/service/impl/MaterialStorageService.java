package com.douniu.imshh.finance.storage.service.impl;

import java.util.List;

import com.douniu.imshh.finance.storage.dao.IMaterialStorageDao;
import com.douniu.imshh.finance.storage.domain.MaterialStorage;
import com.douniu.imshh.finance.storage.service.IMaterialStorageService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialStorageService implements IMaterialStorageService{

	private IMaterialStorageDao dao;
	
	@Override
	public List<MaterialStorage> statisticsStorage(MaterialStorage materialStorage) {
		MaterialStorage condition = LikeFlagUtil.appendLikeFlag(materialStorage, new String[]{"materialName"});
		return dao.statisticsStorage(condition);
	}

	@Override
	public int countStorage(MaterialStorage materialStorage) {
		MaterialStorage condition = LikeFlagUtil.appendLikeFlag(materialStorage, new String[]{"materialName"});
		return dao.countStorage(condition);
	}

	public void setDao(IMaterialStorageDao dao) {
		this.dao = dao;
	}

	
}
