package com.douniu.imshh.material.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.douniu.imshh.utils.RequestParameterLoader;

@Controller
@RequestMapping("/mtl")
public class MaterialAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("品名","name",0));  
		mapper.add(new ExcelBean("规格1","specification1",0)); 
		mapper.add(new ExcelBean("规格2","specification2",0)); 
		mapper.add(new ExcelBean("规格3","specification3",0)); 		  
		mapper.add(new ExcelBean("分类","category",0));  
		mapper.add(new ExcelBean("计量单位","unit",0));
		mapper.add(new ExcelBean("库存","storage",0));
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IMaterialService service;
	
	@Authorization("010101")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("010101")
	@RequestMapping(value="/getMaterialById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getMaterialById(String id){
		Material material = service.getById(id);
		return GsonUtil.toJson(material);
	}
	
	
	@Authorization("010102")
	@RequestMapping(value="/addMaterial", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void addMaterial(Material material){
		service.newMaterial(material);
	}
	
	@Authorization("010103")
	@RequestMapping(value="/deleteMaterial")
	@ResponseBody
	public void deleteMaterial(String id){
		service.deleteMaterial(id);
	}
	
	@Authorization("010104")
	@RequestMapping(value="importMaterial",method={RequestMethod.GET,RequestMethod.POST}, produces = "text/html; charset=utf-8")  
    @ResponseBody
	public String importMaterial(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
        List<Material> materials = POIExcelAdapter.toDomainList(data, mapper, Material.class);
        List<ImportException> checkResults = service.checkImport(materials);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importMaterial(materials);
        return "success";
	}
	
	@Authorization("010105")
	@RequestMapping(value = "exportMaterial", method = RequestMethod.GET, produces = "text/html; charset=utf-8")  
    @ResponseBody  
	public void exportMaterial(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		ImportAndExportUtil.exportPreprocess(response, "原材料品类列表");
		
		String[] params = {"name", "category", "supplier"};
		MaterialFilter filter = new MaterialFilter();
		RequestParameterLoader.loadParameter(request, filter, params);
		List<Material> materialList = service.exportMaterial(filter);
		
        Workbook workbook=null;  
        try {
        	workbook = POIExcelAdapter.toWorkBook(materialList, mapper, Material.class); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        ImportAndExportUtil.exportProcess(response, workbook);
	}
}
