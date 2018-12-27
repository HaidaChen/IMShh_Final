package com.douniu.imshh.product.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.BillDetail;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductOutBill;
import com.douniu.imshh.product.domain.ProductOutTableRow;
import com.douniu.imshh.product.service.IProductOutService;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.RequestParameterLoader;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/pdtout")
public class ProductOutAction {
	
	@Autowired
	private IProductOutService service;
	@Autowired
	private IParameterService pservice;
	
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(ProductFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
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
	
	@RequestMapping(value="/deleteProductOut", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void deleteProductOut(String id){
		service.deleteProductOut(id);
	}
	
	@RequestMapping(value = "exportProductOut", method = RequestMethod.GET, produces = "text/html; charset=utf-8")  
    @ResponseBody  
	public void exportProductOut(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		ImportAndExportUtil.exportPreprocess(response, "原材料入库明细");
		
		String[] params = {"number", "billReason", "startDate", "endDate", "pdtId"};
		ProductFilter filter = new ProductFilter();
		RequestParameterLoader.loadParameter(request, filter, params);
		List<ProductOutTableRow> productInList = service.exportBill(filter);
		
        /*Workbook workbook=null;  
        try {
        	workbook = POIExcelAdapter.toWorkBook(productInList, mapper, ProductInTableRow.class); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        ImportAndExportUtil.exportProcess(response, workbook);*/
	}
}
