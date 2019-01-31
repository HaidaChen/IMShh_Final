package com.douniu.imshh.material.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.BillDetail;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInBill;
import com.douniu.imshh.material.domain.MaterialInTableRow;
import com.douniu.imshh.material.service.ICategoryService;
import com.douniu.imshh.material.service.IMaterialInService;
import com.douniu.imshh.supplier.service.ISupplierService;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/mtlin")
public class MaterialInAction {
	@Autowired
	private IMaterialInService service;
	@Autowired
	private IParameterService pservice;
	@Autowired
	private ISupplierService suppService;
	@Autowired
	private ICategoryService ctgService;
	
	@Authorization("010201")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("010201")
	@RequestMapping(value ="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		MaterialInBill in = service.getById(id);
		return GsonUtil.toJson(in, null);
	}
	
	@Authorization("010202")
	@RequestMapping(value ="/getBillCode", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBillCode(){
		String code = pservice.getParam("bill.materialin.code");
		String preFix = "CLRKD";
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String dateStr = format.format(new Date());
		if (StringUtils.isEmpty(code)){
			code = preFix + dateStr + ".001";
		}else{
			String _dateStr = code.substring(preFix.length(), code.indexOf("."));
			if (!dateStr.equals(_dateStr)){
				code = preFix + dateStr + ".001";
			}else{
				String num = code.substring(code.indexOf(".") + 1);
				String new_num = String.format("%0" + num.length() + "d", Integer.parseInt(num) + 1);
				code = preFix + _dateStr + "." + new_num;
			}
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		return GsonUtil.toJson(map);
	}
	
	@Authorization("010202")
	@RequestMapping(value="/newMaterialIn", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void newMaterialIn(MaterialInBill materialIn, String billItem){
		List<BillDetail> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<BillDetail>>(){}.getType());
		List<BillDetail> details = new ArrayList<>();
		for (BillDetail detail : _details){
			if (detail.getMaterial()!= null && !StringUtils.isEmpty(detail.getMaterial().getId())){
				details.add(detail);
			}
		}
		materialIn.setDetails(details);
		service.newMaterialIn(materialIn);
		pservice.setParam("bill.materialin.code", materialIn.getNumber());
	}
	
	@Authorization("010203")
	@RequestMapping(value="/updateMaterialIn", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateMaterialIn(MaterialInBill materialIn, String billItem){
		List<BillDetail> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<BillDetail>>(){}.getType());
		List<BillDetail> details = new ArrayList<>();
		for (BillDetail detail : _details){
			if (detail.getMaterial()!= null && !StringUtils.isEmpty(detail.getMaterial().getId())){
				details.add(detail);
			}
		}
		materialIn.setDetails(details);
		service.updateMaterialIn(materialIn);
	}
	
	@Authorization("010204")
	@RequestMapping(value="/deleteMaterialIn", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void deleteMaterialIn(String id){
		service.deleteMaterialIn(id);
	}
	
	@RequestMapping(value = "exportMaterialIn", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportMaterialIn(HttpServletRequest request, HttpServletResponse response, MaterialFilter filter){
		SheetData data = new SheetData("材料入库单列表");
		data.put("number", filter.getNumber());
		if (!StringUtils.isEmpty(filter.getSupplier())){
			data.put("supplier", suppService.getById(filter.getSupplier()).getName());
		}
		if (!StringUtils.isEmpty(filter.getCtgCode())){
			data.put("materialCtg", ctgService.getByCode(filter.getCtgCode()));
		}
		data.put("startDate", filter.getStartDate());
		data.put("endDate", filter.getEndDate());
		List<MaterialInBill> bills = service.query(filter);
		
		int startRowNum = 7;
		int numberColumn = 1;
		int billDateColumn = 2;
		int supplierColumn = 3;
		
		List<CellRangeAddress> ranges = new ArrayList<>();
		List<MaterialInTableRow> tableRows = new ArrayList<>();
		
		for (MaterialInBill bill : bills){
			List<BillDetail> details = bill.getDetails();
			for (BillDetail detail : details){
				tableRows.add(new MaterialInTableRow(bill, detail));
			}
			
			ranges.add(new CellRangeAddress(startRowNum, startRowNum + details.size() - 1, numberColumn, numberColumn));
			ranges.add(new CellRangeAddress(startRowNum, startRowNum + details.size() - 1, billDateColumn, billDateColumn));
			ranges.add(new CellRangeAddress(startRowNum, startRowNum + details.size() - 1, supplierColumn, supplierColumn));
			startRowNum = startRowNum + details.size() - 1;
		}
		data.addDatas(tableRows);
		data.setRanges(ranges);
		ImportAndExportUtil.export("材料入库单列表.xls", data, request, response);
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
