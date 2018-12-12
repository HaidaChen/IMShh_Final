package com.douniu.imshh.material.service.impl;

import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialInDao;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialIn;
import com.douniu.imshh.material.domain.MaterialInDetail;
import com.douniu.imshh.material.service.IMaterialInService;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialInService implements IMaterialInService{

	private IMaterialInDao dao;
	private IMaterialService mtlService;
	
	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"number", "remark"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public MaterialIn getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void newMaterialIn(MaterialIn materialIn) {
		IDInjector.injector(materialIn);
		List<MaterialInDetail> details = materialIn.getDetails();
		for (MaterialInDetail detail : details){
			detail.setBillId(materialIn.getId());
			mtlService.addStorage(detail.getMaterial().getId(), detail.getAmount());
		}
		IDInjector.injector(details);
		dao.insertDetails(details);
		dao.insert(materialIn);
	}

	@Override
	public void updateMaterialIn(MaterialIn materialIn) {
		MaterialIn o_materialIn = dao.getById(materialIn.getId());
		List<MaterialInDetail> o_details = o_materialIn.getDetails();
		for (MaterialInDetail detail : o_details){
			mtlService.addStorage(detail.getMaterial().getId(), 0-detail.getAmount());
		}
		
		dao.update(materialIn);
		dao.deleteDetailsByBillId(materialIn.getId());
		dao.insertDetails(materialIn.getDetails());
		for (MaterialInDetail detail : materialIn.getDetails()){
			mtlService.addStorage(detail.getMaterial().getId(), detail.getAmount());
		}
	}

	@Override
	public void deleteMaterialIn(String id) {
		MaterialIn materialIn = dao.getById(id);
		dao.delete(id);
		dao.deleteDetailsByBillId(id);
		List<MaterialInDetail> details = materialIn.getDetails();
		for (MaterialInDetail detail : details){
			mtlService.addStorage(detail.getMaterial().getId(), 0-detail.getAmount());
		}
	}
	
	
	/*private IMaterialInDao dao;
	private IMaterialService mtlService;
	
	@Override
	public List<MaterialIn> query(MaterialFilter filter) {
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name"});
		return dao.query(condition);
	}

	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}
	
	@Override
	public MaterialIn getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void inStorage(MaterialIn materialIn) {
		IDInjector.injector(materialIn);
		dao.insert(materialIn);
		mtlService.addStorage(materialIn.getMaterialId(), materialIn.getAmount());
	}

	@Override
	public void batchInStorage(List<MaterialIn> materialIn) {
		IDInjector.injector(materialIn);
		dao.batchInsert(materialIn);
		for (MaterialIn item : materialIn){
			mtlService.addStorage(item.getMaterialId(), item.getAmount());
		}
	}
	
	@Override
	public void importMaterialIn(List<MaterialIn> materialInList) {
		List<Material> fullMaterial = mtlService.query(new MaterialFilter());
		//List<MaterialSupplier> fullSupplier = suppService.query(new MaterialFilter());
		for (MaterialIn item : materialInList){
			Material mtl = new Material();
			mtl.setName(item.getMaterialName());
			mtl.setSpecification(item.getSpecification());
			int mi = fullMaterial.indexOf(mtl);
			item.setMaterialId(fullMaterial.get(mi).getId());
			
			MaterialSupplier supp = new MaterialSupplier();
			supp.setName(item.getSupplierName());
			int si = fullSupplier.indexOf(supp);
			item.setSupplierId(fullSupplier.get(si).getId());
		}
		batchInStorage(materialInList);
	}

	@Override
	public List<ImportException> checkImport(List<MaterialIn> materialInList) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String unassociation_material = "";
		String unassociation_supplier = "";
		List<Material> fullMaterials = mtlService.query(new MaterialFilter());
		//List<MaterialSupplier> fullSuppliers = suppService.query(new MaterialFilter());
		
		for (int i = 0; i < materialInList.size(); i++){
			MaterialIn materialin = materialInList.get(i);
			
			Material material = new Material();
			material.setName(materialin.getMaterialName());
			material.setSpecification(materialin.getSpecification());
			if (!fullMaterials.contains(material)){
				unassociation_material += "," + (i+2);
			}
			
			MaterialSupplier supplier = new MaterialSupplier();
			supplier.setName(materialin.getSupplierName());
			if (!fullSuppliers.contains(supplier)){
				unassociation_supplier += "," + (i+2);
			}
		}
		if (!unassociation_material.equals("")){
			exceptions.add(new ImportException("无法关联原材料", "原材料名称、规格1、规格2、规则3；与系统中已经存在的原材料品类不匹配，导致无法关联", unassociation_material.substring(1), ""));
		}
		if (!unassociation_supplier.equals("")){
			exceptions.add(new ImportException("无法关联供应商", "供应商名称与系统中已经存在供应商不匹配，导致无法关联", unassociation_supplier.substring(1), ""));			
		}
		return exceptions;
	}
	
	@Override
	public List<MaterialIn> exportMaterialIn(MaterialFilter filter) {
		// TODO Auto-generated method stub
		return query(filter);
	}
*/
	public void setDao(IMaterialInDao dao) {
		this.dao = dao;
	}

	public void setMtlService(IMaterialService mtlService) {
		this.mtlService = mtlService;
	}

	
}
