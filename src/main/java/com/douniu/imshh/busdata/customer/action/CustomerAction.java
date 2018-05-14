package com.douniu.imshh.busdata.customer.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.douniu.imshh.busdata.customer.domain.Customer;
import com.douniu.imshh.busdata.customer.service.ICustomerService;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.ExcelUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;


@Controller
@RequestMapping("/cust")
public class CustomerAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("公司名称","name",0));  
		mapper.add(new ExcelBean("公司地址","address",0));  
		mapper.add(new ExcelBean("联系电话","phone",0));   
		mapper.add(new ExcelBean("邮箱","email",0));  
		mapper.add(new ExcelBean("传真","fax",0));  
		mapper.add(new ExcelBean("联系人","contacts",0));  
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private ICustomerService service;
	
	@RequestMapping(value="/edit", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String edit(Customer cust){		
		Customer customer = service.getById(cust.getId());
		Gson gson = new Gson();
		return gson.toJson(customer);
	}
	
	@RequestMapping(value="/findbyname", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findByName(String custname){
		Customer customer = service.getByName(custname);
		Gson gson = new Gson();
		return gson.toJson(customer);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(Customer cust){
		service.save(cust);
        return 1;
	}
	
	@RequestMapping(value ="/loadcust", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadCustomer(Customer cust){
		List<Customer> res = service.query(cust);
		int count = service.count(cust);
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new Gson();
        return gson.toJson(pr);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}	
	
    @RequestMapping(value="importcustomer",method={RequestMethod.GET,RequestMethod.POST})  
    @ResponseBody  
    public void importCustomer(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在");  
        }  
          
        in = file.getInputStream();  
        List<List<Object>>  data = ExcelUtil.parseExcel(in, file.getOriginalFilename());
        List<Customer> customers = POIExcelAdapter.toDomainList(data, mapper, Customer.class);
        service.batchAdd(customers);
    }  
    
    @RequestMapping(value = "exportcustomer", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportCustomer(HttpServletRequest request,HttpServletResponse response,HttpSession session){  
        response.reset();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssms");  
        String dateStr = sdf.format(new Date());  
         
        response.setHeader("Content-Disposition", "attachment;filename=" +dateStr+".xlsx");  
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
  
        Workbook workbook=null;  
        try {
        	String condition = request.getParameter("condition");
        	Customer customer = new Customer();
        	customer.setCondition(condition);
            List<Customer> customers = service.queryNoPage(customer);
        	workbook = POIExcelAdapter.toWorkBook(customers, mapper, Customer.class); 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        OutputStream output;  
        try {  
            output = response.getOutputStream();  
          
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
            bufferedOutPut.flush();  
            workbook.write(bufferedOutPut);  
            bufferedOutPut.close();  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
}
