package com.douniu.imshh.busdata.product.action;

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

import com.douniu.imshh.busdata.product.domain.Product;
import com.douniu.imshh.busdata.product.service.IProductService;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.utils.ExcelBean;
import com.douniu.imshh.utils.ExcelUtil;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.POIExcelAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/pdt")
public class ProductAction {
	private static List<ExcelBean> mapper = new ArrayList<ExcelBean>();
	static{
		mapper.add(new ExcelBean("货号","code",0));  
		mapper.add(new ExcelBean("产品名称","name",0));  
		mapper.add(new ExcelBean("规格","specification",0));   
		mapper.add(new ExcelBean("型号","model",0)); 
		mapper.add(new ExcelBean("备注","remark",0));  
	}
	
	@Autowired
	private IProductService service;
	
	@RequestMapping(value="/edit", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String edit(Product pdt){
		Product product = service.getById(pdt.getId());
		Gson gson = new Gson();
        return gson.toJson(product);
	}
	
	@RequestMapping(value="/findbycode", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findByCode(String code){
		Product product = service.getByCode(code);
		Gson gson = new Gson();
        return gson.toJson(product);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(Product pdt){
		service.save(pdt);
        return 1;
	}
	
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(Product pdt){
		List<Product> list = service.query(pdt);
		return GsonUtil.toJson(list);
	}
	
	@RequestMapping(value ="/loadpdt", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadProduct(Product pdt){
		List<Product> res = service.query(pdt);
		int count = service.count(pdt);

		PageResult pr = new PageResult();
		pr.setTotal(count);
		pr.setRows(res);
				
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String resJson = gson.toJson(pr);
		return resJson;
	}
	
	@RequestMapping(value ="/loadallpdt", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadAllProduct(Product pdt){
		pdt.setPageSize(10000);
		List<Product> res = service.query(pdt);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String resJson = gson.toJson(res);
		return resJson;
	}	
	
	@RequestMapping(value ="/loadpdtbyorder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryByOrder(String identify){
		List<Product> res = service.queryByOrder(identify);
		
		Gson gson = new Gson();
        return gson.toJson(res);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}
	
	
	
    @ResponseBody  
    @RequestMapping(value="importproduct",method={RequestMethod.GET,RequestMethod.POST})  
    public  void  importProduct(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
          
        InputStream in =null;  
        MultipartFile file = multipartRequest.getFile("upfile");  
        if(file.isEmpty()){  
            throw new Exception("文件不存在");  
        }  
          
        in = file.getInputStream();  
        List<List<Object>>  data = ExcelUtil.parseExcel(in, file.getOriginalFilename());
        List<Product> products = POIExcelAdapter.toDomainList(data, mapper, Product.class);
        service.batchAdd(products);
    }  
    
    @RequestMapping(value = "exportproduct", method = RequestMethod.GET)  
    @ResponseBody  
    public void exportProduct(HttpServletRequest request,HttpServletResponse response,HttpSession session){  
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
        	Product product = new Product();
        	product.setCondition(condition);
            List<Product> products = service.queryNoPage(product);
        	workbook = POIExcelAdapter.toWorkBook(products, mapper, Product.class); 
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
