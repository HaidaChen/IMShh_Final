package com.douniu.imshh.material.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Category;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;

@Controller
@RequestMapping("/mtl")
public class MaterialAction {
	
	@Autowired
	private IMaterialService service;
	
	@Authorization("0501")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("0501")
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(MaterialFilter filter){
		List<Material> materials = service.query(filter);
		return GsonUtil.toJson(materials);
	}
	
	@Authorization("0501")
	@RequestMapping(value="/getMaterialById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getMaterialById(String id){
		Material material = service.getById(id);
		return GsonUtil.toJson(material);
	}
	
	@RequestMapping(value="/validateUnique", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String validateUnique(MaterialFilter filter){
		boolean result = service.exist(filter);
		Map<String, Boolean> map = new HashMap<>();
		map.put("valid", !result);
		return GsonUtil.toJson(map);
	}
	
	@Authorization("0501")
	@RequestMapping(value="/addMaterial", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void addMaterial(Material material){
		service.newMaterial(material);
	}
		
	@Authorization("0501")
	@RequestMapping(value="/updateMaterial", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateMaterial(Material material){
		service.updateMaterial(material);
	}
	
	@Authorization("0501")
	@RequestMapping(value="/deleteMaterial")
	@ResponseBody
	public void deleteMaterial(String id){
		service.deleteMaterial(id);
	}
	
	@Authorization("0501")
	@RequestMapping(value="importMaterial",method={RequestMethod.GET,RequestMethod.POST}, produces = "text/html; charset=utf-8")  
    @ResponseBody
	public String importMaterial(HttpServletRequest request) throws Exception{
		List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
        List<Material> materials = new ArrayList<>();
		for (List<Object> rowData : data){
			Material mtl = new Material();
			mtl.setName(rowData.get(0).toString());
			mtl.setSpecification(rowData.get(1).toString());
			Category ctg = new Category();
			ctg.setCode(rowData.get(2).toString());
			mtl.setCtg(ctg);
			mtl.setUnit(rowData.get(3).toString());
			mtl.setStorage(new Float(rowData.get(4).toString()));
			mtl.setRemark(rowData.get(5).toString());
			materials.add(mtl);
		}
		List<ImportException> checkResults = service.checkImport(materials);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importMaterial(materials);
        return "success";
	}
	
	@Authorization("0501")
	@RequestMapping(value = "exportMaterial", method = RequestMethod.GET, produces = "text/html; charset=utf-8")  
    @ResponseBody  
	public void exportMaterial(HttpServletRequest request,HttpServletResponse response, MaterialFilter filter){
		SheetData data = new SheetData("原材料列表");
		/*data.put("name", filter.getName());
		data.put("ctgCode", filter.getCtgCode());
		data.put("specification", filter.getSpecification());
		data.put("lowerStorage", filter.getLowerStorage());
		data.put("upperStorage", filter.getUpperStorage());
		data.put("remark", filter.getRemark());*/
		
		List<Material> materials = service.exportMaterial(filter);
		data.addDatas(materials);
		ImportAndExportUtil.export("原材料列表.xls", data, request, response);
	}
}
