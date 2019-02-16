package com.douniu.imshh.product.action;

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
import com.douniu.imshh.product.domain.BillDetail;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductOutBill;
import com.douniu.imshh.product.domain.ProductOutTableRow;
import com.douniu.imshh.product.service.IProductOutService;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/pdtout")
public class ProductOutAction {
	
	@Autowired
	private IProductOutService service;
	@Autowired
	private IParameterService pservice;
	
	@Authorization("0304")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(ProductFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@Authorization("0304")
	@RequestMapping(value ="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		ProductOutBill out = service.getById(id);
		return GsonUtil.toJson(out, null);
	}
	
	@RequestMapping(value ="/getBillCode", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBillCode(){
		String code = pservice.getParam("bill.productout.code");
		String preFix = "CPCKD";
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
	
	@Authorization("0302")
	@RequestMapping(value="/newProductOut", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void newProductOut(ProductOutBill productOut, String billItem){
		List<BillDetail> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<BillDetail>>(){}.getType());
		List<BillDetail> details = new ArrayList<>();
		for (BillDetail detail : _details){
			if (detail.getProduct()!= null && !StringUtils.isEmpty(detail.getProduct().getId())){
				details.add(detail);
			}
		}
		productOut.setDetails(details);
		service.newProductOut(productOut);
		pservice.setParam("bill.productout.code", productOut.getNumber());
	}
	
	@Authorization("0302")
	@RequestMapping(value="/updateProductOut", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateProductOut(ProductOutBill productOut, String billItem){
		List<BillDetail> _details = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(billItem, new TypeToken<List<BillDetail>>(){}.getType());
		List<BillDetail> details = new ArrayList<>();
		for (BillDetail detail : _details){
			if (detail.getProduct()!= null && !StringUtils.isEmpty(detail.getProduct().getId())){
				details.add(detail);
			}
		}
		productOut.setDetails(details);
		service.updateProductOut(productOut);
	}
	
	@Authorization("0302")
	@RequestMapping(value="/deleteProductOut", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void deleteProductOut(String id){
		service.deleteProductOut(id);
	}
	
	@Authorization("0304")
	@RequestMapping(value = "exportProductOut", method = RequestMethod.GET, produces = "text/html; charset=utf-8")  
    @ResponseBody  
	public void exportProductOut(HttpServletRequest request,HttpServletResponse response, ProductFilter filter){
		SheetData data = new SheetData("成品出库单列表");
		data.put("number", filter.getNumber());
		data.put("startDate", filter.getStartDate());
		data.put("endDate", filter.getEndDate());
		List<ProductOutBill> bills = service.query(filter);
		
		int startRowNum = 6;
		int numberColumn = 1;
		int billDateColumn = 2;
		
		List<CellRangeAddress> ranges = new ArrayList<>();
		List<ProductOutTableRow> tableRows = new ArrayList<>();
		
		for (ProductOutBill bill : bills){
			List<BillDetail> details = bill.getDetails();
			for (BillDetail detail : details){
				tableRows.add(new ProductOutTableRow(bill, detail));
			}
			
			ranges.add(new CellRangeAddress(startRowNum, startRowNum + details.size() - 1, numberColumn, numberColumn));
			ranges.add(new CellRangeAddress(startRowNum, startRowNum + details.size() - 1, billDateColumn, billDateColumn));
			startRowNum = startRowNum + details.size();
		}
		data.addDatas(tableRows);
		data.setRanges(ranges);
		ImportAndExportUtil.export("成品出库单列表.xls", data, request, response);
	}
}
