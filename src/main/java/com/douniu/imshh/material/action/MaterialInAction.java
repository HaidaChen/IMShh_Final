package com.douniu.imshh.material.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialIn;
import com.douniu.imshh.material.domain.MaterialInDetail;
import com.douniu.imshh.material.service.IMaterialInService;
import com.douniu.imshh.utils.GsonUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/mtlin")
public class MaterialInAction {
	@Autowired
	private IMaterialInService service; 
	
	@Authorization("010201")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("010202")
	@RequestMapping(value="/newMaterialIn", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void newMaterialIn(MaterialIn materialIn, String billItem){
		List<MaterialInDetail> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<MaterialInDetail>>(){}.getType());
		List<MaterialInDetail> details = new ArrayList<>();
		for (MaterialInDetail detail : _details){
			if (detail.getMaterial()!= null && !StringUtils.isEmpty(detail.getMaterial().getId())){
				details.add(detail);
			}
		}
		materialIn.setDetails(details);
		service.newMaterialIn(materialIn);
	}
	
	@Authorization("010203")
	@RequestMapping(value="/updateMaterialIn", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateMaterialIn(MaterialIn materialIn){
		service.updateMaterialIn(materialIn);
	}
	
	@Authorization("010204")
	@RequestMapping(value="/deleteMaterialIn", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void deleteMaterialIn(String id){
		service.deleteMaterialIn(id);
	}
	
	/*private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("入库日期","inDate",0));
		mapper.add(new ExcelBean("供应商","supplierName",0));   
		mapper.add(new ExcelBean("品名","materialName",0));  
		mapper.add(new ExcelBean("规格1","specification1",0));  
		mapper.add(new ExcelBean("规格2","specification2",0));		
		mapper.add(new ExcelBean("规格3","specification3",0));
		mapper.add(new ExcelBean("单位","unit",0));
		mapper.add(new ExcelBean("数量","amount",0));;
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IMaterialInService service; 
	
	@Authorization("010201")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("010202")
	@RequestMapping(value="/inStorage", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void inStorage(MaterialIn materialIn){
		service.inStorage(materialIn);
	}
	
	@Authorization("010202")
	@RequestMapping(value="/pasteInStorage", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void pasteInStorage(String materialStr, Date inDate){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();;
		List<MaterialIn> materialIns =gson.fromJson(materialStr, new TypeToken<List<MaterialIn>>() {}.getType());
		for (MaterialIn materialIn : materialIns){
			materialIn.setInDate(inDate);
		}
		service.batchInStorage(materialIns);
	}
	
	@Authorization("010202")
	@RequestMapping(value="/batchInStorage", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void batchInStorage(String materialStr, Date inDate, String supplier){
		Gson gson = new Gson();
		List<MaterialIn> materialIns =gson.fromJson(materialStr, new TypeToken<List<MaterialIn>>() {}.getType());
		for (MaterialIn materialIn : materialIns){
			materialIn.setInDate(inDate);
			materialIn.setSupplierId(supplier);
		}
		service.batchInStorage(materialIns);
	}
	
	@Authorization("010202")
	@RequestMapping(value="importMaterialIn",method={RequestMethod.GET,RequestMethod.POST}, produces = "text/html; charset=utf-8")  
    @ResponseBody
	public String importMaterialIn(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
        List<MaterialIn> materialIns = POIExcelAdapter.toDomainList(data, mapper, MaterialIn.class);
        List<ImportException> checkResults = service.checkImport(materialIns);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importMaterialIn(materialIns);
        return "success";
	}
	
	@Authorization("010203")
	@RequestMapping(value = "exportMaterialIn", method = RequestMethod.GET, produces = "text/html; charset=utf-8")  
    @ResponseBody  
	public void exportMaterialIn(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		ImportAndExportUtil.exportPreprocess(response, "原材料入库明细");
		
		String[] params = {"name", "supplier", "startDate", "endDate"};
		MaterialFilter filter = new MaterialFilter();
		RequestParameterLoader.loadParameter(request, filter, params);
		List<MaterialIn> materialInList = service.exportMaterialIn(filter);
		
        Workbook workbook=null;  
        try {
        	workbook = POIExcelAdapter.toWorkBook(materialInList, mapper, MaterialIn.class); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        ImportAndExportUtil.exportProcess(response, workbook);
	}*/
}
