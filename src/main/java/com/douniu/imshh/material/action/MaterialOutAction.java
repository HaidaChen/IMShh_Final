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
import com.douniu.imshh.material.domain.MaterialOutBill;
import com.douniu.imshh.material.domain.MaterialOutTableRow;
import com.douniu.imshh.material.service.IMaterialOutService;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/mtlout")
public class MaterialOutAction {
	@Autowired
	private IMaterialOutService service;
	@Autowired
	private IParameterService pservice;
	
	@Authorization("0204")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(MaterialFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("0204")
	@RequestMapping(value ="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		MaterialOutBill bill = service.getById(id);
		return GsonUtil.toJson(bill, null);
	}
	
	@RequestMapping(value ="/getBillCode", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBillCode(){
		String code = pservice.getParam("bill.materialout.code");
		String preFix = "CLCKD";
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
	
	@Authorization("0202")
	@RequestMapping(value="/newBill", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void newBill(MaterialOutBill bill, String billItem){
		List<BillDetail> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<BillDetail>>(){}.getType());
		List<BillDetail> details = new ArrayList<>();
		for (BillDetail detail : _details){
			if (detail.getMaterial()!= null && !StringUtils.isEmpty(detail.getMaterial().getId())){
				details.add(detail);
			}
		}
		bill.setDetails(details);
		service.newBill(bill);
		pservice.setParam("bill.materialout.code", bill.getNumber());
	}
	
	@Authorization("0202")
	@RequestMapping(value="/updateBill", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateBill(MaterialOutBill bill, String billItem){
		List<BillDetail> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<BillDetail>>(){}.getType());
		List<BillDetail> details = new ArrayList<>();
		for (BillDetail detail : _details){
			if (detail.getMaterial()!= null && !StringUtils.isEmpty(detail.getMaterial().getId())){
				details.add(detail);
			}
		}
		bill.setDetails(details);
		service.updateBill(bill);
	}
	
	@Authorization("0202")
	@RequestMapping(value="/deleteBill", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void deleteBill(String id){
		service.deleteBill(id);
	}
	
	@Authorization("0204")
	@RequestMapping(value = "exportMaterialOut", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportMaterialOut(HttpServletRequest request, HttpServletResponse response, MaterialFilter filter){
		SheetData data = new SheetData("材料出库单列表");
		data.put("number", filter.getNumber());
		String reason = "";
		if ("01".equals(filter.getBillReason())){
			reason = "生产出库";
		}
		if ("02".equals(filter.getBillReason())){
			reason = "退货出库";
		}
		if ("03".equals(filter.getBillReason())){
			reason = "其他出库";
		}
		data.put("billReason", reason);
		data.put("startDate", filter.getStartDate());
		data.put("endDate", filter.getEndDate());
		List<MaterialOutBill> bills = service.query(filter);
		
		int startRowNum = 6;
		int numberColumn = 1;
		int billDateColumn = 2;
		
		List<CellRangeAddress> ranges = new ArrayList<>();
		List<MaterialOutTableRow> tableRows = new ArrayList<>();
		
		for (MaterialOutBill bill : bills){
			List<BillDetail> details = bill.getDetails();
			for (BillDetail detail : details){
				tableRows.add(new MaterialOutTableRow(bill, detail));
			}
			
			ranges.add(new CellRangeAddress(startRowNum, startRowNum + details.size() - 1, numberColumn, numberColumn));
			ranges.add(new CellRangeAddress(startRowNum, startRowNum + details.size() - 1, billDateColumn, billDateColumn));
			startRowNum = startRowNum + details.size();
		}
		data.addDatas(tableRows);
		data.setRanges(ranges);
		ImportAndExportUtil.export("材料出库单列表.xls", data, request, response);
	}
	
	/*private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
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
	}*/
}
