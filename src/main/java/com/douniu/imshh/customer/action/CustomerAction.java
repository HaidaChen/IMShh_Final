package com.douniu.imshh.customer.action;

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
import com.douniu.imshh.customer.domain.Customer;
import com.douniu.imshh.customer.domain.CustomerFilter;
import com.douniu.imshh.customer.service.ICustomerService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;
import com.google.gson.Gson;

@Controller
@RequestMapping("/cust")
public class CustomerAction {
	@Autowired
	private ICustomerService service;
	
	@Authorization("0504")
	@RequestMapping(value="/getById", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getById(String id){
		Customer customer = service.getById(id);
		Gson gson = new Gson();
        return gson.toJson(customer);
	}
	
	@Authorization("0504")
	@RequestMapping(value="/existCustomer", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String existCustomer(String id, String name){
		Map<String, Boolean> result = new HashMap<>();
		result.put("valid", true);
		List<Customer> custs = service.query(new CustomerFilter());
		for (Customer cust : custs){
			if (name.equals(cust.getName()) && !id.equals(cust.getId())){
				result.put("valid", false);
				break;
			}
		}
		return GsonUtil.toJson(result);
	}
	
	@Authorization("0504")
	@RequestMapping(value="/newCustomer", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void newCustomer(Customer cust){
		service.newCustomer(cust);
	}
	
	@Authorization("0504")
	@RequestMapping(value="/updateCustomer", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void updateCustomer(Customer cust){
		service.updateCustomer(cust);
	}
	
	@Authorization("0504")
	@RequestMapping(value ="/getPageResult", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPageResult(CustomerFilter filter){
		PageResult pr = service.getPageResult(filter);
		return GsonUtil.toJson(pr, null);
	}
	
	@RequestMapping(value ="/loadallcust", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadAllCustomer(CustomerFilter supp){
		List<Customer> res = service.query(supp);
		
		Gson gson = new Gson();
        return gson.toJson(res);
	}
	
	@Authorization("0504")
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}
	
	@Authorization("0504")
    @ResponseBody  
    @RequestMapping(value="importCustomer",method={RequestMethod.GET,RequestMethod.POST})  
    public String importCustomer(HttpServletRequest request) throws Exception {  
    	List<List<Object>> data = ImportAndExportUtil.importPreprocess(request);
		List<Customer> customers = new ArrayList<>();
		for (List<Object> rowData : data){
			Customer customer = new Customer();
			customer.setName(rowData.get(0).toString());
			customer.setAddress(rowData.get(1).toString());
			customer.setFax(rowData.get(2).toString());
			customer.setContacts(rowData.get(3).toString());
			customer.setPhone(rowData.get(4).toString());
			customer.setRemark(rowData.get(5).toString());
			customers.add(customer);
		}
		List<ImportException> checkResults = service.checkImport(customers);
        if (!checkResults.isEmpty())
        	return GsonUtil.toJson(checkResults);
        service.importCustomer(customers);
        return "success";
    }  
    
	@Authorization("0504")
    @RequestMapping(value = "exportCustomer", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportCustomer(HttpServletRequest request,HttpServletResponse response, CustomerFilter filter){  
    	SheetData data = new SheetData("客户列表");
		data.put("custName", filter.getCustName());
		List<Customer> customers = service.query(filter);
		data.addDatas(customers);
		ImportAndExportUtil.export("客户列表.xls", data, request, response);
    } 
}
