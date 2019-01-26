package com.douniu.imshh.material.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInOut;
import com.douniu.imshh.material.domain.MaterialInOutMap;
import com.douniu.imshh.material.service.IMaterialInOutService;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;

@Controller
@RequestMapping("/mtlio")
public class MaterialInOutAction {
	@Autowired
	private IMaterialInOutService service;
	@Autowired
	private IMaterialService mtlService;
	
	@RequestMapping(value ="/getGlobalInOutPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getGlobalInOutPageResult(MaterialFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		PageResult rs = service.getGlobalInOutPageResult(filter);
		return GsonUtil.toJson(rs, null);
	}
	
	@RequestMapping(value = "exportGlobalInOut", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportGlobalInOut(HttpServletRequest request, HttpServletResponse response, MaterialFilter filter){
		String tmpPath = request.getRealPath("/templater/export/材料出入汇总.xls");
		
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		
		List<String> periodList = new ArrayList<>();
		periodList.add(sPeriod);
		String currentPeriod = sPeriod;
		while(!DateUtil.getNexMonth(currentPeriod, "yyyy-MM").equals(ePeriod)){
			periodList.add(DateUtil.getNexMonth(currentPeriod, "yyyy-MM"));
			currentPeriod = DateUtil.getNexMonth(currentPeriod, "yyyy-MM");
		}
		periodList.add(ePeriod);
		
		List<CellRangeAddress> ranges = new ArrayList<>();
		SheetData data = new SheetData("材料出入汇总");
		data.put("startPeriod", sPeriod);
		data.put("endPeriod", ePeriod);
		
		Workbook wb = null;
		try {
			int sDynRow = 5;
			int sDynCol = 2;
			InputStream input = new FileInputStream(tmpPath);
			wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);
			Row period_row = sheet.getRow(sDynRow);
			Row io_row = sheet.getRow(sDynRow + 1);
			Row ioExp_row = sheet.getRow(sDynRow + 2);
			Cell head_cell = period_row.getCell(sDynCol);
			Cell exp_cell = ioExp_row.getCell(2);
			int periodCount = 0;
			for (String period : periodList){
				Cell periodCell = period_row.createCell(sDynCol + periodCount * 2 + 1);
				Cell emptyCell = period_row.createCell(sDynCol + periodCount * 2 + 2);
				periodCell.setCellStyle(head_cell.getCellStyle());
				emptyCell.setCellStyle(head_cell.getCellStyle());
				String cellDesc = period.replace("-", "年");
				periodCell.setCellValue(cellDesc+"月");
				ranges.add(new CellRangeAddress(sDynRow, sDynRow, sDynCol + periodCount * 2 + 1, sDynCol + periodCount * 2 + 2));
				
				Cell inCell = io_row.createCell(sDynCol + periodCount * 2 + 1);
				Cell outCell = io_row.createCell(sDynCol + periodCount * 2 + 2);
				inCell.setCellStyle(head_cell.getCellStyle());
				outCell.setCellStyle(head_cell.getCellStyle());
				inCell.setCellValue("入库数");
				outCell.setCellValue("出库数");
				
				Cell inExpCell = ioExp_row.createCell(sDynCol + periodCount * 2 + 1);
				Cell outExpCell = ioExp_row.createCell(sDynCol + periodCount * 2 + 2);
				inExpCell.setCellStyle(exp_cell.getCellStyle());
				outExpCell.setCellStyle(exp_cell.getCellStyle());
				String periodExp = period.replace("-", "");
				inExpCell.setCellValue("${iomap."+periodExp+".inQuantity}");
				outExpCell.setCellValue("${iomap."+periodExp+".outQuantity}");
				periodCount++;				
			}
			Cell storageCell = period_row.createCell(sDynCol + periodCount * 2 + 1);
			storageCell.setCellStyle(head_cell.getCellStyle());
			storageCell.setCellValue("当前库存");
			ranges.add(new CellRangeAddress(sDynRow, sDynRow+1, sDynCol + periodCount * 2 + 1, sDynCol + periodCount * 2 + 1));
			
			Cell storageExpCell = ioExp_row.createCell(sDynCol + periodCount * 2 + 1);
			storageExpCell.setCellStyle(exp_cell.getCellStyle());
			storageExpCell.setCellValue("${storage}");
			
			data.setRanges(ranges);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<MaterialInOutMap> inoutmap = service.queryGlobalInOut(filter);
		data.addDatas(inoutmap);
		ImportAndExportUtil.export(wb, data, request, response);
	}
	
	@RequestMapping(value ="/getInOutByMaterial", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getInOutByMaterial(String materialId, String sPeriod, String ePeriod){
		List<MaterialInOut> ios = new ArrayList<>();
		if (!StringUtils.isEmpty(materialId) && 
				!StringUtils.isEmpty(sPeriod) && 
				!StringUtils.isEmpty(ePeriod)){
			ios = service.getInOutByMaterial(materialId, sPeriod, ePeriod);
			
		}
		return GsonUtil.toJson(ios, "yyyy-MM-dd");
	}
	
	@RequestMapping(value = "exportInOutByMaterial", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportInOutByMaterial(HttpServletRequest request, HttpServletResponse response, MaterialFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		String materialId = filter.getMaterialId();
		
		SheetData data = new SheetData("材料出入明细");
		Material mtl = mtlService.getById(materialId);
		data.put("material", mtl.getName() + " " + mtl.getSpecification());
		data.put("startPeriod", sPeriod);
		data.put("endPeriod", ePeriod);
		
		List<MaterialInOut> ios = service.getInOutByMaterial(materialId, sPeriod, ePeriod);
		data.addDatas(ios);
		ImportAndExportUtil.export("材料出入明细.xls", data, request, response);
	}
}
