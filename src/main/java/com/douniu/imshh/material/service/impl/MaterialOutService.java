package com.douniu.imshh.material.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialOutDao;
import com.douniu.imshh.material.domain.BillDetail;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialOutBill;
import com.douniu.imshh.material.domain.MaterialOutTableRow;
import com.douniu.imshh.material.service.IMaterialOutService;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialOutService implements IMaterialOutService{
	private IMaterialOutDao dao;
	private IMaterialService mtlService;
	
	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"number"});
		
		List<MaterialOutBill> result = dao.getPageResult(condition);
		pr.setRows(change2TableRows(result));
		pr.setTotal(dao.count(condition));
		return pr;
	}
	
	@Override
	public List<MaterialOutBill> query(MaterialFilter filter) {
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"number"});
		return dao.query(condition);
	}

	@Override
	public MaterialOutBill getById(String id) {
		return dao.getById(id);
	}
	@Override
	public void newBill(MaterialOutBill bill) {
		IDInjector.injector(bill);
		List<BillDetail> details = bill.getDetails();
		for (BillDetail detail : details){
			detail.setBillId(bill.getId());
			mtlService.addStorage(detail.getMaterial().getId(), 0 - detail.getQuantity());
		}
		IDInjector.injector(details);
		dao.insertDetails(details);
		dao.insert(bill);
	}
	@Override
	public void updateBill(MaterialOutBill bill) {
		MaterialOutBill o_bill = dao.getById(bill.getId());
		List<BillDetail> o_details = o_bill.getDetails();
		for (BillDetail detail : o_details){
			mtlService.addStorage(detail.getMaterial().getId(), detail.getQuantity());
		}
		
		dao.update(bill);
		dao.deleteDetailsByBillId(bill.getId());
		List<BillDetail> details = bill.getDetails();
		for (BillDetail detail : details){
			detail.setBillId(bill.getId());
			mtlService.addStorage(detail.getMaterial().getId(), 0 - detail.getQuantity());
		}
		IDInjector.injector(details);
		dao.insertDetails(bill.getDetails());
	}
	
	@Override
	public void deleteBill(String id) {
		MaterialOutBill bill = dao.getById(id);
		dao.delete(id);
		dao.deleteDetailsByBillId(id);
		List<BillDetail> details = bill.getDetails();
		for (BillDetail detail : details){
			mtlService.addStorage(detail.getMaterial().getId(), detail.getQuantity());
		}
	}
	
	private List<MaterialOutTableRow> change2TableRows(List<MaterialOutBill> bills){
		List<MaterialOutTableRow> rows = new ArrayList<>();
		for (MaterialOutBill bill : bills){
			List<BillDetail> details = bill.getDetails();
			for (BillDetail detail : details){
				MaterialOutTableRow row = new MaterialOutTableRow(bill, detail);
				rows.add(row);
			}
		}
		return rows;
	}
	/*@Override
	public List<MaterialOut> query(MaterialFilter filter) {
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
	public void outStorage(MaterialOut materialOut) {
		IDInjector.injector(materialOut);
		dao.insert(materialOut);
		materialService.addStorage(materialOut.getMaterialId(), 0-materialOut.getAmount());
	}

	@Override
	public void batchOutStorage(List<MaterialOut> materialOuts) {
		IDInjector.injector(materialOuts);
		dao.batchInsert(materialOuts);
		for (MaterialOut item : materialOuts){
			materialService.addStorage(item.getMaterialId(), 0-item.getAmount());
		}
	}

	

	@Override
	public MaterialOut getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void importMaterialOut(List<MaterialOut> materialOutList) {
		List<Material> fullMaterial = materialService.query(new MaterialFilter());
		for (MaterialOut item : materialOutList){
			Material mtl = new Material();
			mtl.setName(item.getMaterialName());
			mtl.setSpecification(item.getSpecification());
			int mi = fullMaterial.indexOf(mtl);
			item.setMaterialId(fullMaterial.get(mi).getId());
		}
		batchOutStorage(materialOutList);
	}

	@Override
	public List<ImportException> checkImport(List<MaterialOut> materialOutList) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String unassociation_material = "";
		List<Material> fullMaterials = materialService.query(new MaterialFilter());
		
		for (int i = 0; i < materialOutList.size(); i++){
			MaterialOut materialout = materialOutList.get(i);
			
			Material material = new Material();
			material.setName(materialout.getMaterialName());
			material.setSpecification(materialout.getSpecification());
			if (!fullMaterials.contains(material)){
				unassociation_material += "," + (i+2);
			}
		}
		if (!unassociation_material.equals("")){
			exceptions.add(new ImportException("无法关联原材料", "原材料名称、规格1、规格2、规则3；与系统中已经存在的原材料品类不匹配，导致无法关联", unassociation_material.substring(1), ""));
		}
		return exceptions;
	}

	@Override
	public List<MaterialOut> exportMaterialOut(MaterialFilter filter) {
		return query(filter);
	}
*/
	public void setDao(IMaterialOutDao dao) {
		this.dao = dao;
	}

	public void setMtlService(IMaterialService mtlService) {
		this.mtlService = mtlService;
	}
	
}
