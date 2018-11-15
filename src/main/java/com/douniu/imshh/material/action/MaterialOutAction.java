package com.douniu.imshh.material.action;

import java.util.ArrayList;
import java.util.Date;
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
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialOut;
import com.douniu.imshh.material.service.IMaterialOutService;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.douniu.imshh.utils.RequestParameterLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/mtlOut")
public class MaterialOutAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("出库日期","outDate",0));
		mapper.add(new ExcelBean("品名","materialName",0));  
		mapper.add(new ExcelBean("规格1","specification1",0));  
		mapper.add(new ExcelBean("规格2","specification2",0));		
		mapper.add(new ExcelBean("规格3","specification3",0));
		mapper.add(new ExcelBean("单位","unit",0));
		mapper.add(new ExcelBean("数量","amount",0));;
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IMaterialOutService service; 
	
	@Authorization("010301")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("010302")
	@RequestMapping(value="/outStorage", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void outStorage(MaterialOut materialOut){
		service.outStorage(materialOut);
	}
	

	@Authorization("010302")
	@RequestMapping(value="/pasteOutStorage", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void pasteOutStorage(String materialStr, Date outDate){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();;
		List<MaterialOut> materialOuts =gson.fromJson(materialStr, new TypeToken<List<MaterialOut>>() {}.getType());
		for (MaterialOut materialOut : materialOuts){
			materialOut.setOutDate(outDate);
		}
		service.batchOutStorage(materialOuts);
	}
	
	@Authorization("010302")
	@RequestMapping(value="/batchOutStorage", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void batchOutStorage(String materialStr, Date outDate){
		Gson gson = new Gson();
		List<MaterialOut> materialOuts =gson.fromJson(materialStr, new TypeToken<List<MaterialOut>>() {}.getType());
		for (MaterialOut materialOut : materialOuts){
			materialOut.setOutDate(outDate);
		}
		service.batchOutStorage(materialOuts);
	}
	
	@Authorization("010302")
	@RequestMapping(value="importMaterialOut",method={RequestMethod.GET,RequestMethod.POST}, produces = "text/html; charset=utf-8")  
    @ResponseBody
	public String importMaterialOut(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
        List<MaterialOut> materialOuts = POIExcelAdapter.toDomainList(data, mapper, MaterialOut.class);
        List<ImportException> checkResults = service.checkImport(materialOuts);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importMaterialOut(materialOuts);
        return "success";
	}
	
	@Authorization("010303")
	@RequestMapping(value = "exportMaterialOut", method = RequestMethod.GET, produces = "text/html; charset=utf-8")  
    @ResponseBody  
	public void exportMaterialOut(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		ImportAndExportUtil.exportPreprocess(response, "原材料出库明细");
		
		String[] params = {"name", "startDate", "endDate"};
		MaterialFilter filter = new MaterialFilter();
		RequestParameterLoader.loadParameter(request, filter, params);
		List<MaterialOut> materialOutList = service.exportMaterialOut(filter);
		
        Workbook workbook=null;  
        try {
        	workbook = POIExcelAdapter.toWorkBook(materialOutList, mapper, MaterialOut.class); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        ImportAndExportUtil.exportProcess(response, workbook);
	}
}
