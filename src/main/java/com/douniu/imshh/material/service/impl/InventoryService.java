package com.douniu.imshh.material.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IInventoryDao;
import com.douniu.imshh.material.domain.Inventory;
import com.douniu.imshh.material.domain.InventoryDetail;
import com.douniu.imshh.material.domain.InventoryMap;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.IInventoryService;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class InventoryService implements IInventoryService {
	private IInventoryDao dao;
	private IMaterialService mtlService;
	/**
	 * 保存盘点结果，所有实际盘点数据都暂存在临时表中，所以不需要传递参数
	 */
	public void inventory(Inventory inventory){
		/*
		 * 盘点的本质是，人为的对物品进行数量上的盘查，可以获得物品库存的最准确的数据，
		 * 那么第一步需要保存盘点的结果，并更新系统的库存
		 * 其中，有一个细节问题：系统会根据历史出入库记录给出系统盘点结果，如果按照时间段进行统计，那么数据
		 * 可能存在不精确的问题，比如，2018-12-12日上午盘点给出了结果，并保存了数据；那么当天又发生了新的
		 * 出入库，在下次系统盘点时将无法统计到下午发生的出入库数据；因为系统目前所有的出入库记录是按照日期
		 * 来记录的。
		 * */
		//1、保存盘点记录
		IDInjector.injector(inventory);
		dao.saveInventory(inventory);
		dao.saveInventoryDetail(inventory.getId());
		
		//2、更新库存
		dao.updateStorage();
		
		//3、为出入库明细记录打标签
		dao.markInventory();
		
		//4、清空缓存表
		dao.removeCash();
		
	}
		
	/*
	 * 有一个细节问题：系统会根据历史出入库记录给出系统盘点结果，如果按照时间段进行统计，那么数据
	 * 可能存在不精确的问题，比如，2018-12-12日上午盘点给出了结果，并保存了数据；那么当天又发生了新的
	 * 出入库，在下次系统盘点时将无法统计到下午发生的出入库数据；因为系统目前所有的出入库记录是按照日期
	 * 来记录的。
	 * 解决方案，不以时间为界限，针对每条记录打标签，
	 * */
	public PageResult getSystemInventory(MaterialFilter filter){
		
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name"});
		if (!StringUtils.isEmpty(condition.getCtgCode())){
			condition.setCtgCode(condition.getCtgCode() + "%");
		}
		
		PageResult pr = new PageResult();
		/*该结果集应该包含系统计算值、缓存值*/
		List<InventoryDetail> rs = dao.queryStorageWithCachePageResult(condition);
		int count = dao.countStorageWithCache(condition);
		
		pr.setRows(rs);
		pr.setTotal(count);
		return pr;
	}
	
	@Override
	public void importCacheItem(List<InventoryDetail> inventoryDetails) {
		List<Material> allMaterial = mtlService.query(new MaterialFilter());
		List<InventoryDetail> target = new ArrayList<>();
		initCashInventory();
		for (InventoryDetail detail : inventoryDetails){
			int pos = allMaterial.indexOf(detail.getMaterial());
			Material m = allMaterial.get(pos);
			detail.setMaterial(m);
			target.add(detail);
		}
		dao.batchInsertCacheItem(target);
	}

	@Override
	public List<ImportException> checkImport(List<InventoryDetail> inventoryDetails) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String unassociation_material = "";
		List<Material> allMaterial = mtlService.query(new MaterialFilter());
		for (int i = 0; i < inventoryDetails.size(); i++){
			inventoryDetails.get(i).getMaterial();
			if(!allMaterial.contains(inventoryDetails.get(i).getMaterial())){
				unassociation_material += "," + (i+2);
			}
		}
		if (!unassociation_material.equals("")){
			exceptions.add(new ImportException("无法关联原材料", "原材料名称、规格；与系统中已经存在的原材料品类不匹配，导致无法关联", unassociation_material.substring(1), ""));
		}
		return exceptions;
	}

	/**
	 * 初始化缓存表
	 */
	public void initCashInventory(){
		dao.removeCash();
	}
	
	public void saveCacheItem(String materialId, float quantity){
		int size = dao.existCacheItem(materialId);
		if (size == 0){
			dao.insertCacheItem(materialId, quantity);
		}
		if (size == 1){
			dao.updateCacheItem(materialId, quantity);
		}
		
	}
	
	public List<Inventory> queryInventory(MaterialFilter filter){
		return dao.queryInventory(filter);
	}
	
	public PageResult queryInventoryDetailPageResult(MaterialFilter filter){
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name"});
		if (!StringUtils.isEmpty(condition.getCtgCode())){
			condition.setCtgCode(condition.getCtgCode() + "%");
		}
		
		List<InventoryMap> maps = new ArrayList<>();
		
		PageResult pr = new PageResult();
		List<InventoryDetail> rs = dao.queryInventoryDetailByPage(condition);
		
		for(InventoryDetail detail : rs){
			InventoryMap map = null;
			if (!maps.contains(detail)){
				map = new InventoryMap(detail.getMaterial());
				maps.add(map);
			}else {
				map = maps.get(maps.indexOf(detail));
			}
			map.getStorageMap().put(detail.getInventoryId(), detail.getActualQuantity());
		}
			
		
		int count = dao.countInventoryDetail(condition);
		pr.setRows(maps);
		pr.setTotal(count);
		return pr;
	}
	
	public Inventory getById(String id){
		return dao.getById(id);
	}

	public void setDao(IInventoryDao dao) {
		this.dao = dao;
	}

	public void setMtlService(IMaterialService mtlService) {
		this.mtlService = mtlService;
	}
}
