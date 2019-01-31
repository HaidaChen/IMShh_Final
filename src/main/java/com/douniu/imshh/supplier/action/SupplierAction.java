package com.douniu.imshh.supplier.action;

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

import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.supplier.domain.Supplier;
import com.douniu.imshh.supplier.domain.SupplierFilter;
import com.douniu.imshh.supplier.service.ISupplierService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;
import com.google.gson.Gson;

@Controller
@RequestMapping("/supp")
public class SupplierAction {
	
	@Autowired
	private ISupplierService service;
	
	
	@RequestMapping(value="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		Supplier supplier = service.getById(id);
		Gson gson = new Gson();
        return gson.toJson(supplier);
	}
	
	@RequestMapping(value="/existSupplier", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String existSupplier(String id, String name){
		Map<String, Boolean> result = new HashMap<>();
		result.put("valid", true);
		List<Supplier> suppers = service.query(new SupplierFilter());
		for (Supplier supper : suppers){
			if (name.equals(supper.getName()) && !id.equals(supper.getId())){
				result.put("valid", false);
				break;
			}
		}
		return GsonUtil.toJson(result);
	}
	
	@RequestMapping(value="/newSupplier", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void newSupplier(Supplier supp){
		service.newSupplier(supp);
	}
	
	@RequestMapping(value="/updateSupplier", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateSupplier(Supplier supp){
		service.updateSupplier(supp);
	}
	
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(SupplierFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@RequestMapping(value ="/loadallsupp", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadAllSupplier(SupplierFilter supp){
		List<Supplier> res = service.query(supp);
		
		Gson gson = new Gson();
        return gson.toJson(res);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}
	
    @ResponseBody  
    @RequestMapping(value="importSupplier",method={RequestMethod.GET,RequestMethod.POST})  
    public String importSupplier(HttpServletRequest request) throws Exception {  
    	List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
		List<Supplier> suppliers = new ArrayList<>();
		for (List<Object> rowData : data){
			Supplier supplier = new Supplier();
			supplier.setName(rowData.get(0).toString());
			supplier.setAddress(rowData.get(1).toString());
			supplier.setFax(rowData.get(2).toString());
			supplier.setContacts(rowData.get(3).toString());
			supplier.setPhone(rowData.get(4).toString());
			supplier.setRemark(rowData.get(5).toString());
			suppliers.add(supplier);
		}
		List<ImportException> checkResults = service.checkImport(suppliers);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importSupplier(suppliers);
        return "success";
    }  
    
    @RequestMapping(value = "exportSupplier", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportSupplier(HttpServletRequest request,HttpServletResponse response, SupplierFilter filter){  
    	SheetData data = new SheetData("供应商列表");
		data.put("suppName", filter.getSuppName());
		List<Supplier> suppliers = service.query(filter);
		data.addDatas(suppliers);
		ImportAndExportUtil.export("供应商列表.xls", data, request, response);
    }  
}
