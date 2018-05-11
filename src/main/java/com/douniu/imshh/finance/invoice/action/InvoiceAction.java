package com.douniu.imshh.finance.invoice.action;

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

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.invoice.domain.Invoice;
import com.douniu.imshh.finance.invoice.service.IInvoiceService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.ExcelUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/invoice")
public class InvoiceAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("开票日期","invoiceDate",0));  
		mapper.add(new ExcelBean("客户名称","customerName",0));  
		mapper.add(new ExcelBean("价税合计","amountWithTax",0));   
		mapper.add(new ExcelBean("应交增值税","valueAddTax",0));  
		mapper.add(new ExcelBean("应交消费税","exciseTax",0));  
		mapper.add(new ExcelBean("城建税","constructionTax",0));		
		mapper.add(new ExcelBean("教育费附加","educationFee",0));
		mapper.add(new ExcelBean("税款合计","totalTax",0));
		mapper.add(new ExcelBean("退税","drawback",0));
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IInvoiceService service;
	
	@RequestMapping(value="/edit", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String edit(Invoice invoice){
		Invoice oInvoice = service.getById(invoice.getId());
		Gson gson = new Gson();
        return gson.toJson(oInvoice);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(Invoice invoice){
		service.save(invoice);
        return 1;
	}
	
	@RequestMapping(value ="/loadinvoice", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadInvoice(Invoice invoice){
		List<Invoice> res = service.query(invoice);
		int count = service.count(invoice);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}
	
	
    @ResponseBody  
    @RequestMapping(value="importinvoice",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  importInvoice(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在");  
        }  
          
        in = file.getInputStream();  
        List<List<Object>>  data = ExcelUtil.parseExcel(in, file.getOriginalFilename());
        List<Invoice> Invoices = POIExcelAdapter.toDomainList(data, mapper, Invoice.class);
        service.batchAdd(Invoices);
    }  
    
    @RequestMapping(value = "exportinvoice", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportInvoice(HttpServletRequest request,HttpServletResponse response,HttpSession session){  
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
        	String customerName = request.getParameter("customerName");
        	Date startDate = DateUtil.string2Date(request.getParameter("startDate"));
        	Date endDate = DateUtil.string2Date(request.getParameter("endDate"));
        	
        	Invoice invoice = new Invoice();
        	invoice.setCustomerName(customerName);
        	invoice.setStartDate(startDate);
        	invoice.setEndDate(endDate);
        	
            List<Invoice> invoices = service.queryNoPage(invoice);
        	workbook = POIExcelAdapter.toWorkBook(invoices, mapper, Invoice.class); 
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
